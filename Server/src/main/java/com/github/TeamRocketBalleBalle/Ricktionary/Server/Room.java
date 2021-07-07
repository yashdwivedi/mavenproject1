package com.github.TeamRocketBalleBalle.Ricktionary.Server;

// TODO: implement this

import ThisIsWhereTheMagicHappens.GameMode;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Order;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.LoadScene;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.OrderTypeLookupTable;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Room implements Runnable {

    private final Logger logger;
    private final ArrayList<Player> playerArray = new ArrayList<Player>();
    private String hash;
    private final int tickRate = 1000;
    private final GameMode gameMode = new GameMode(); // TODO: do something about this.
    private final Queue<PlayersInput> inputs = new LinkedList<>();
    private final HashMap<Player, Integer> scores = new HashMap<>();

    {
        logger = LoggerFactory.getLogger("Ricktionary.Server.Room");
    }

    public void add(Player player) {
        playerArray.add(player);
        player.addUserInputTo(inputs);
        player.send(
                PacketType.LOAD_SCENE, OrderTypeLookupTable.LOAD_SCENE, new Order<String>("wait"));
        logger.debug("added {} to this room", player.getName());
    }

    public boolean isReady() {
        return playerArray.toArray().length == 1;
    }

    public void run() {

        logger.info("New Room created");
        pingCheck();

        startSetup();

        // send game scenes
        Order<String> gameOn = new Order<>("gameOn");

        for (Player player : playerArray) {
            player.send(PacketType.LOAD_SCENE, OrderTypeLookupTable.LOAD_SCENE, gameOn);
            player.setStoreInput(true);
            logger.debug("sent {} to {}", gameOn, player.getName());
        }

        logger.debug("sent all clients order to change screen");

        long tickStartTime = System.currentTimeMillis();
        int tick = 0;
        logger.debug("Starting game loop");
        // Game Loop is ON 🔥🔥🔥🔥
        while (!gameMode.ended()) {
            if (tick < tickRate) {
                // TODO: sanitise input. one day
                tick = (int) (System.currentTimeMillis() - tickStartTime);
                //                logger.debug("waiting for tick -> inside if");
            } else {
                //                logger.debug("insdie else block");
                synchronized (inputs) {
                    ArrayList<PlayersInput> playersInputs = new ArrayList<>();
                    for (PlayersInput input : inputs) {
                        playersInputs.add(inputs.remove());
                    }

                    logger.debug("processing: {}", playersInputs);
                    for (Map.Entry<Player, Integer> entry :
                            gameMode.playTurn(playersInputs).entrySet()) {
                        int new_score = scores.getOrDefault(entry.getKey(), 0) + entry.getValue();
                        scores.put(entry.getKey(), new_score);
                    }
                    //                    logger.debug("processed input");
                    tellEveryone(playersInputs);
                    tickStartTime = System.currentTimeMillis();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.endGame();

        // start tick count
        // while gamemode.ended!=true

    }

    private void tellEveryone(ArrayList<PlayersInput> playersInputs) {
        for (PlayersInput value : playersInputs) {
            if (!value.getTheirInput().isBlank()) {
                Order<String> chatMessage = new Order<>(value.getTheirInput());
                for (Player player : playerArray) {
                    player.send(
                            PacketType.CHAT_MESSAGE, OrderTypeLookupTable.CHAT_MSG, chatMessage);
                }
                logger.info("Sent message : {}", chatMessage);
            }
        }
    }

    public void endGame() {
        Player winner = null;
        for (Map.Entry<Player, Integer> candidate : scores.entrySet()) {
            Integer currentScore = candidate.getValue();
            if (winner == null) {
                winner = candidate.getKey();
            } else if (scores.get(winner) < candidate.getValue()) {
                winner = candidate.getKey();
            }
        }
        assert winner != null;
        winner.send(
                PacketType.LOAD_SCENE,
                OrderTypeLookupTable.LOAD_SCENE,
                new Order<Integer>(LoadScene.WINNER_SCENE));
        for (Player player : playerArray) {
            if (!player.equals(winner)) {
                player.send(
                        PacketType.LOAD_SCENE,
                        OrderTypeLookupTable.LOAD_SCENE,
                        new Order<Integer>(LoadScene.LOSER_SCENE));
            }
        }
    }

    public String getImageHash() {
        Random rand = new Random();

        //        String[] hash = Database.getAllImageHash(); // function to be implemented that
        // will send an array of all the hashes of images from the database
        //        int lengthOfHash = hash.length;
        //
        //        String choosenHash = hash[rand.nextInt(lengthOfHash)];

        //        return choosenHash;
        //        logger.debug("choosenHash {}", choosenHash);
        //
        //        return choosenHash;
        return ("");
    }

    public void startSetup() {
        hash = getImageHash();
        Order<String> imageOrder = new Order<>(hash);
        for (Player player : playerArray) {
            player.send(PacketType.LOAD_IMG, OrderTypeLookupTable.LOAD_IMAGE, imageOrder);
        }
        boolean anyOneHasNotLoadedImg = true; // starting value
        logger.debug("starting while loop");
        while (anyOneHasNotLoadedImg) {
            anyOneHasNotLoadedImg = false;
            for (Player player : playerArray) {
                if (!player.isLoaded()) {
                    anyOneHasNotLoadedImg = true;
                }
            }
        }
    }

    public static void main(String args[]) { // -----------------------MAIN CLASS------------//

        Room imageLoader = new Room();
        String hash = imageLoader.getImageHash();
        System.out.println("hash of the image is -->" + hash);
    }

    public void pingCheck() {
        Thread thread =
                new Thread(
                        () -> {
                            while (true) {
                                synchronized (playerArray) {
                                    playerArray.removeIf(Player::isDisconnected);
                                }

                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        thread.start();
    }
}
