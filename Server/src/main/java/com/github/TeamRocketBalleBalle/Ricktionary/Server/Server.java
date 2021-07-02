package com.github.TeamRocketBalleBalle.Ricktionary.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    String ip;
    int port;
    private final Logger logger;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        logger = LoggerFactory.getLogger("Rictionary.Server");
    }

    public Server() {
        this("127.0.0.1", 5000);
    }

    public static void main(String[] args) {
        new Server().listen();
    }

    public void listen() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("server started");
        } catch (IOException ex) {
            logger.error("Something happened in Server: ", ex);
        }
    }
}
