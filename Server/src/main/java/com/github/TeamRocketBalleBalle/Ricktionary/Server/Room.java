package com.github.TeamRocketBalleBalle.Ricktionary.Server;

// TODO: implement this

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Room implements Runnable {
    private final Logger logger;
    {
        logger = LoggerFactory.getLogger("Ricktionary.Server.Room");
    }
    public void add(Player player) {}

    public boolean isReady() {
        return true;
    }

    public void run() {
        logger.info("New Room created");
    }

    public void end() {}
}
