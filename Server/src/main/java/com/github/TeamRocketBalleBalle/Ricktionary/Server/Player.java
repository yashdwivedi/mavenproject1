package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ClientPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Order;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.Reply;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms.ServerPacket;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.OrderTypeLookupTable;
import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Player implements Runnable {

    private Logger logger;

    private final Socket socket;

    private final ObjectInputStream objReader;
    private final ObjectOutputStream objWriter;

    private String name;

    private HashMap<Player, String> roomsInput;

    private boolean storeInput;
    private final HashMap<Order<?>, Reply<?>> pendingReplies;

    /* TODO: prolly add code here to send an Order to client to send the player name from them */
    /* TODO: also maybe have the client send a response as OrderResponse object? */
    public Player(Socket socket) throws IOException {
        pendingReplies = new HashMap<>();

        this.socket = socket;

        objWriter = new ObjectOutputStream(socket.getOutputStream());
        objReader = new ObjectInputStream(socket.getInputStream());

        name = null;

        logger =
                LoggerFactory.getLogger(
                        "Ricktionary.Player."
                                + socket.getInetAddress().toString().substring(1)
                                + name);
        new Thread(this::read).start();
    }

    public void addUserInputTo(HashMap<Player, String> roomsInput) {
        this.roomsInput = roomsInput;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public Reply<?> getReply(Order<?> order) {
        logger.debug("PendingReplies map: {}", pendingReplies.toString());
        Reply<?> reply = null;
        while (reply == null) {
            synchronized (pendingReplies) {
                reply = pendingReplies.get(order);
            }
            try {
                Thread.sleep(0, 1000); // sleep for 1 micro second
            } catch (InterruptedException e) {
                logger.error("Error while getting data from map", e);
            }
        }
        synchronized (this) {
            pendingReplies.remove(order, reply);
        }
        return reply;
    }

    public void initialise() {
        Order<String> nameOrder = new Order<>("nameOrder");
        send(PacketType.INITIALISE, OrderTypeLookupTable.INITIALISE, nameOrder);
        Reply<String> reply = (Reply<String>) getReply(nameOrder);
        logger.debug("got reply: {}", reply);
        synchronized (logger) {
            this.name = (String) reply.getValue();
            logger =
                    LoggerFactory.getLogger(
                            "Ricktionary.Player." + socket.getInetAddress() + this.name);
        }
        logger.info(
                "Set null:{} to {}:{}",
                socket.getInetAddress(),
                this.name,
                socket.getInetAddress());
    }

    /**
     * This is the method that reads inputs from the client and parses it accordingly
     */
    public void read() {
        logger.debug("read thread started");
        while (!socket.isClosed()) {
            logger.debug("inside loop");
            try {

                ClientPacket receivedPacket = (ClientPacket) objReader.readObject();
                logger.debug("Data received: {}", (ClientPacket) receivedPacket);
                switch (receivedPacket.getPacketType()) {
                    case 0 -> {
                        synchronized (pendingReplies) {
                            pendingReplies.put(
                                    receivedPacket.getReplyingTo(),
                                    (Reply<String>) receivedPacket.getReply());
                            logger.debug("put {} in pendingReplies", receivedPacket.getReply());
                        }
                    }
                    case 1 -> {
                        logger.debug("Order Reply received");
                        pendingReplies.put(
                                receivedPacket.getReplyingTo(), receivedPacket.getReply());
                    }
                    case 5 -> {
                        // synchronize the inputs dictionary
                        synchronized (roomsInput) {
                            logger.debug("string input received");
                            if (storeInput) {
                                roomsInput.put(this, (String) receivedPacket.getReply().getValue());
                            }
                        }
                    }
                }

            } catch (IOException ex) {
                logger.error("IOException in Player", ex);
            } catch (ClassNotFoundException ex) {
                logger.error("Class not found in Player", ex);
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                logger.error("Error while sleeping", e);
            }
        }
    }

    public void run() {
        this.read();
    }

    public void send(byte packetType, byte orderType, Order<?> order) {
        ServerPacket packet = new ServerPacket(packetType, orderType, order);
        try {
            objWriter.writeObject(packet);
            objWriter.flush();
            logger.debug(
                    "Sent a packet to {}:{} of type {},{},{}",
                    name,
                    socket.getInetAddress(),
                    packetType,
                    orderType,
                    order);
        } catch (IOException ex) {
            logger.error("Error while sending a packet", ex);
        }
    }

    public void sendChatMessage(String message) {
        Order<String> stringOrder = new Order<>(message);
        send(PacketType.GAME_STATE, OrderTypeLookupTable.CHAT_MSG, stringOrder);
    }
}
