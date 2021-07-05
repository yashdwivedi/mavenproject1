package com.github.TeamRocketBalleBalle.Ricktionary.Server;

// TODO: implement this

import ch.qos.logback.core.net.SyslogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class Room implements Runnable {

    public String getImageHash(){
        Random rand = new Random();

//        String[] hash = Database.getAllImageHash(); // function to be implemented that will send an array of all the hashes of images from the database
        int lengthOfHash = hash.length ;

        String choosenHash = hash[rand.nextInt(lengthOfHash)];

//        return choosenHash;
        logger.debug("choosenHash {}",choosenHash);

        return choosenHash;
    }


    private final Logger logger;

    {
        logger = LoggerFactory.getLogger("Ricktionary.Server.Room");
    }

    public void add(Player player)
    {

    }

    public boolean isReady() {
        return true;
    }

    public void run() {

        logger.info("New Room created");

        // send game scenes
        // start tick count
        //while gamemode.ended!=true

    }

    public void end() {
    }

    public static void main(String args[]){                                 //-----------------------MAIN CLASS------------//

        Room imageLoader = new Room();
        String hash =  imageLoader.getImageHash();
        System.out.println("hash of the image is -->"+hash);

    }

}

