package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import ThisIsWhereTheMagicHappens.GameMode;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Order;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.LoadScene;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.OrderTypeLookupTable;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.database.DbWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Room implements Runnable {

    private final Logger logger;
    private final ArrayList<Player> playerArray = new ArrayList<Player>();
    private String hash;
    private final int tickRate = 1000;
    private final GameMode gameMode =
            new GameMode("answer ki string value here"); // TODO: do something about this.
    private final Queue<PlayersInput> inputs = new LinkedList<>();
    private final HashMap<Player, Integer> scores = new HashMap<>();

    {
        logger = LoggerFactory.getLogger("Ricktionary.Server.Room");
    }

    public void add(Player player) {
        playerArray.add(player);
        player.addUserInputTo(inputs);
        player.send(
                PacketType.LOAD_SCENE,
                OrderTypeLookupTable.LOAD_SCENE,
                new Order<>(LoadScene.MATCHMAKING_SCENE));
        logger.debug("added {} to this room", player.getName());
    }

    public boolean isReady() {
        return playerArray.toArray().length == 2;
    }

    public void run() {

        logger.info("New Room created");
        pingCheck();

        startSetup();

        // send game scenes
        Order<Integer> gameOn = new Order<Integer>(LoadScene.GAME_SCENE);

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
        while (!gameMode.ended() && playerArray.size() != 0) {
            if (tick <= tickRate) {
                // TODO: sanitise input. one day
                tick = (int) (System.currentTimeMillis() - tickStartTime);
                //                logger.debug("waiting for tick -> inside if");
            } else {
                //                logger.debug("insdie else block");
                synchronized (inputs) {
                    ArrayList<PlayersInput> playersInputs = new ArrayList<>();
                    for (PlayersInput ignored : inputs) { // change based on IDE suggestion
                        playersInputs.add(inputs.remove());
                    }
                    if ( 0 < inputs.size()) {
                        logger.debug("processing: {}", playersInputs);
                    }
                    for (Map.Entry<Player, Integer> entry :
                            gameMode.playTurn(playersInputs).entrySet()) {
                        int new_score = scores.getOrDefault(entry.getKey(), 0) + entry.getValue();
                        scores.put(entry.getKey(), new_score);
                    }
                    tellEveryone(playersInputs);
                    tellScores(scores);
                    // load next image if someone has guessed the answer
                    if (gameMode.isNextImage()) {
                        startSetup();
                        gameMode.setNextImage(false);
                    }
                    tickStartTime = System.currentTimeMillis();
                    tick = 0;
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

    private void tellScores(HashMap<Player, Integer> scores) {
        if (0 < scores.size()){
            ArrayList<Map.Entry<String, Integer>> scoreList = new ArrayList<>();
            for (Map.Entry<Player, Integer> score :
                    scores.entrySet()) {
                scoreList.add(new AbstractMap.SimpleEntry<>(score.getKey().getName(), score.getValue()));
            }
            for (Player player :
                    playerArray) {
                player.send(PacketType.GAME_STATE, OrderTypeLookupTable.GAME_STATE, new Order<>(scoreList));
            }
        }
    }

    private void tellEveryone(ArrayList<PlayersInput> playersInputs) {
        for (PlayersInput value : playersInputs) {
            if (!value.getTheirInput().isBlank()) {
                // check if this player is a winner winner?
                String name = value.isSpecialMessage() ? "SERVER" : value.getThem().getName();

                AbstractMap.SimpleEntry<String, String> chatMessage =
                        new AbstractMap.SimpleEntry<>(name, value.getTheirInput());

                value.setSpecialMessage(false);
                for (Player player : playerArray) {
                    player.send(
                            PacketType.CHAT_MESSAGE,
                            OrderTypeLookupTable.CHAT_MSG,
                            new Order<>(chatMessage));
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
        if (winner != null) {
            winner.send(
                    PacketType.LOAD_SCENE,
                    OrderTypeLookupTable.LOAD_SCENE,
                    new Order<Integer>(LoadScene.WINNER_SCENE));
        }
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

        ArrayList<String> hash = DbWork.getListOfHashes(); // function to be implemented that
        //         will send an array of all the hashes of images from the database
        int lengthOfHash = hash.size();

        String choosenHash = hash.get(rand.nextInt(lengthOfHash));

        logger.debug("choosenHash {}", choosenHash);
        return choosenHash;
    }

    public void startSetup() {
        hash = getImageHash();
        logger.info("Choosen image hash: {}", hash);
        // set game mode answer
        gameMode.setAnswer(DbWork.getAnswer(hash));
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
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        thread.start();
    }
}
