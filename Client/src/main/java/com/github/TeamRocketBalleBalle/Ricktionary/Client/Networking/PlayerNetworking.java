package com.github.TeamRocketBalleBalle.Ricktionary.Client.Networking;

import com.github.TeamRocketBalleBalle.Ricktionary.Client.GameScreen;
import com.github.TeamRocketBalleBalle.Ricktionary.Client.test;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ClientPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Order;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Reply;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ServerPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.LoadScene;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.database.DbWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class PlayerNetworking {
    private static Socket socket;
    private static Logger logger;

    // define the obj readers & writers
    private static ObjectInputStream objReader;
    private static ObjectOutputStream objWriter;

    private static final ArrayList<Map.Entry<Byte, Order<?>>> pendingOrders = new ArrayList<>();

    private static boolean disconnected = false;

    public static void setUpNetworking(String hostName, int port) throws IOException {
        socket = new Socket(hostName, port);
        logger = LoggerFactory.getLogger("Ricktionary.Client");

        objReader = new ObjectInputStream(socket.getInputStream());
        objWriter = new ObjectOutputStream(socket.getOutputStream());

        new Thread(PlayerNetworking::read).start();
    }

    public static void read() {
        logger.debug("Read thread started");
        while (!socket.isClosed()) {
            try {
                ServerPacket receivedPacket = (ServerPacket) objReader.readObject();
                logger.debug(
                        "Data received: {},{}",
                        receivedPacket.getPacketType(),
                        receivedPacket.getOrder());

                switch (receivedPacket.getPacketType()) {
                    case PacketType.INITIALISE -> {
                        synchronized (pendingOrders) {
                            pendingOrders.add(
                                    new AbstractMap.SimpleEntry<>(
                                            PacketType.INITIALISE, receivedPacket.getOrder()));
                        }
                    }
                    case PacketType.LOAD_SCENE -> {
                        switch ((Integer) receivedPacket.getOrder().getValue()) {
                            case LoadScene.MATCHMAKING_SCENE -> test.sceneSwitch(
                                    "matchmakingscreen");

                            case LoadScene.GAME_SCENE -> test.sceneSwitch("gamescreen");

                            case LoadScene.WINNER_SCENE -> test.sceneSwitch("winnerscreen");

                            case LoadScene.LOSER_SCENE -> test.sceneSwitch("loserscreen");
                        }
                    }
                    case PacketType.LOAD_IMG -> {
                        String hash = (String) receivedPacket.getOrder().getValue();
                        if (loadImage(hash)) {
                            send(PacketType.LOAD_IMG, receivedPacket.getOrder(), new Reply<>(true));
                        }
                    }
                    case PacketType.CHAT_MESSAGE -> {
                        Map.Entry<String, String> message =
                                (Map.Entry<String, String>) (receivedPacket.getOrder().getValue());
                        logger.debug(
                                "Chat message received: {}: {}",
                                message.getKey(),
                                message.getValue());
                        GameScreen.getDisplay()
                                .append(message.getKey() + ": " + message.getValue() + "\n\n");
                    }
                    case PacketType.GAME_STATE -> {
                        updateScores(
                                (ArrayList<Map.Entry<String, Integer>>)
                                        receivedPacket.getOrder().getValue());
                    }
                }
            } catch (IOException exception) {
                logger.error("IOException while reading", exception);
                disconnected = true;
                break;
            } catch (ClassNotFoundException e) {
                logger.error("Class not found", e);
            }
        }
    }

    private static void updateScores(ArrayList<Map.Entry<String, Integer>> value) {
        value.sort(
                (o1, o2) -> {
                    if (o1.getValue().equals(o2.getValue())) {
                        return 0;
                    }
                    return o1.getValue() < o2.getValue() ? 1 : -1;
                });
        logger.debug("SORTED LIST: {}", value);
        GameScreen.displayTopThree(value);
    }

    public static void send(byte packetType, Order<?> replyingTo, Reply<?> reply) {
        ClientPacket packet = new ClientPacket(packetType, replyingTo, reply);
        try {
            objWriter.writeObject(packet);
            objWriter.flush();
            logger.debug(
                    "Sent a packet to server [{}] of type {},{},{}",
                    socket.getInetAddress(),
                    packetType,
                    replyingTo,
                    reply);
        } catch (IOException ex) {
            logger.error("Error while sending a packet", ex);
        }
    }

    public static boolean isDisconnected() {
        return disconnected;
    }

    public static boolean loadImage(String hash) {
        String imagePath = "/" + DbWork.getImagePath(hash);
        logger.debug("opening image from path: {}", imagePath);
        ImageIcon icon = new ImageIcon(PlayerNetworking.class.getResource(imagePath));
        logger.debug("loaded image height: {}", icon.getIconHeight());
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(416, 594, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        GameScreen.picture.setIcon(scaledIcon);
        return true;
    }

    public static Order<?> getOrder(byte packetType) {
        logger.debug("getting [{}] packet", packetType);
        Order<?> foundOrder = null;
        while (foundOrder == null) {
            int index = 0;
            for (int i = 0; i < pendingOrders.size(); i++) {
                synchronized (pendingOrders) {
                    Map.Entry<Byte, Order<?>> entry = pendingOrders.get(i);
                    if (entry.getKey() == packetType) {
                        foundOrder = entry.getValue();
                        index = i;
                    }
                }
            }
            if (foundOrder != null) {
                synchronized (pendingOrders) {
                    pendingOrders.remove(index);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error("Error while sleeping", e);
            }
        }
        return foundOrder;
    }
}
