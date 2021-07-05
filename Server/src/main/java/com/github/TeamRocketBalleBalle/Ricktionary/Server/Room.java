package com.github.TeamRocketBalleBalle.Ricktionary.Server;

// TODO: implement this

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

public class Room implements Runnable {


    private final Logger logger;
    private final ArrayList<Player> playerArray = new ArrayList<Player>();

    {
        logger = LoggerFactory.getLogger("Ricktionary.Server.Room");
    }

    public void add(Player player) {

        playerArray.add(player);

        System.out.println(playerArray);
    }


    public boolean isReady() {
        return true;
    }

    public void run() {

        logger.info("New Room created");

        // send game scenes
        // start tick count
        // while gamemode.ended!=true

    }


    public void end() {
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


    public static void main(String args[]) { // -----------------------MAIN CLASS------------//

        Room imageLoader = new Room();
        String hash = imageLoader.getImageHash();
        System.out.println("hash of the image is -->" + hash);
    }

}

