package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    String ip;
    int port;
    private final Logger logger;
    private final ArrayList<Room> rooms = new ArrayList<Room>();

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        logger = LoggerFactory.getLogger("Ricktionary.Server");
        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.warn("Shutdown signal received...");
                for (Room room :
                        rooms) {
                    room.end();
                }
            }
        });
    }

    public Server() {
        this("127.0.0.1", 5000);
    }

    public static void main(String[] args) {
        new Server().listen();
    }

    public void listen() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("server started on port {}", port);

            // add the new Room to the list
            rooms.add(new Room());

            while (true) {
                Room currentRoom = rooms.get(0);
                logger.info("Listening for new connections");
                Socket clientSocket = serverSocket.accept();

                // TODO: replace this part with a thread.
                Player player = new Player(clientSocket);
                logger.info("Client: {} ({}) has connected", player.getName(), player.getSocket().getInetAddress());

                // add the player to the room
                currentRoom.add(player);

                // check if Room is ready
                if (currentRoom.isReady()) {
                    Thread thread = new Thread(currentRoom);
                    thread.start();
                    logger.info("New thread created");
                    rooms.add(0, new Room());
                }
            }
        } catch (IOException ex) {
            logger.error("Something happened in Server: ", ex);
        }
    }
}
