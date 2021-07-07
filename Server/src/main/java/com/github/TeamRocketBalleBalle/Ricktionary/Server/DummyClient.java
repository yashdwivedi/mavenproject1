package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ClientPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Reply;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ServerPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class DummyClient {
    public void test() {
        try {
            Logger logger = LoggerFactory.getLogger("Ricktionary.dummyClient");
            Scanner kb = new Scanner(System.in);

            Socket socket = new Socket("127.0.0.1", 5000);
            logger.debug("{}", socket.isConnected());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket.getOutputStream());
            logger.debug("starting loop...");
            while (true) {
                // if (0 < input.available()) {
                logger.debug("Data available to read");
                Object o = input.readObject();
                logger.debug("{}", o);
                ServerPacket serverPacket = (ServerPacket) o;
                logger.debug(
                        "Received: {}, {}", serverPacket.getPacketType(), serverPacket.getOrder());
                switch (serverPacket.getPacketType()) {
                    case 0 -> {
                        logger.debug("sending my name");
                        ClientPacket meme =
                                new ClientPacket(
                                        PacketType.INITIALISE,
                                        ((ServerPacket) o).getOrder(),
                                        new Reply<String>("meme"));
                        out2.writeObject(meme);
                    }
                    case PacketType.LOAD_SCENE -> {
                        logger.info(
                                "Pretend im loading scene: {}", serverPacket.getOrder().getValue());
                        if (serverPacket.getOrder().getValue().equals("gameOn")) {
                            logger.info("ENTER INPUT NOW DUMMY");
                            Thread t =
                                    new Thread() {
                                        public void run() {
                                            System.out.println("you may enter stuff now");
                                            BufferedReader bufferedReader =
                                                    new BufferedReader(
                                                            new InputStreamReader(System.in));
                                            while (true) {
                                                try {
                                                    String input = bufferedReader.readLine();
                                                    ClientPacket textMessage =
                                                            new ClientPacket(
                                                                    PacketType.GAME_INPUT,
                                                                    null,
                                                                    new Reply<>(input));
                                                    out2.writeObject(textMessage);
                                                } catch (IOException exception) {
                                                    exception.printStackTrace();
                                                }

                                                //
                                                //
                                                // ClientPacket textMessage = new
                                                // ClientPacket(PacketType.GAME_INPUT, null, new
                                                // Reply<>("helloo" + System.currentTimeMillis()));
                                                // try {
                                                //
                                                // Thread.sleep(5000);
                                                //
                                                // out2.writeObject(textMessage);
                                                //
                                                // logger.info("sent spam message");
                                                // } catch
                                                // (IOException | InterruptedException exception) {
                                                //
                                                // exception.printStackTrace();
                                                // }
                                            }
                                        }
                                    };
                            t.start();
                        }
                    }
                    case 4 -> {
                        logger.info("Loading image hash: {}", serverPacket.getOrder().getValue());
                        ClientPacket imageLoaded =
                                new ClientPacket(
                                        PacketType.LOAD_IMG,
                                        serverPacket.getOrder(),
                                        new Reply<Boolean>(true));
                        out2.writeObject(imageLoaded);
                        logger.info(
                                "replied to load image: {},{},{}",
                                imageLoaded.getPacketType(),
                                imageLoaded.getReplyingTo(),
                                imageLoaded.getReply());
                    }
                    case 5 -> {
                        System.out.println("send input to server: ");

                        ClientPacket gameInput =
                                new ClientPacket(
                                        PacketType.GAME_INPUT,
                                        serverPacket.getOrder(),
                                        new Reply<>(kb.nextLine()));
                        out2.writeObject(gameInput);
                    }
                    case PacketType.CHAT_MESSAGE -> {
                        logger.info("Chat message: {}", serverPacket.getOrder().getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DummyClient obj = new DummyClient();
        obj.test();
    }
}
