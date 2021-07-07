package com.github.TeamRocketBalleBalle.Ricktionary.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class test extends JFrame {
    static JFrame frame = new JFrame();
    static Map<String, JPanel> map = new HashMap<String, JPanel>();
    static Logger logger = LoggerFactory.getLogger("Ricktionary.SceneSwitcher");

    static {
        map.put("gamescreen", new GameScreen());
        map.put("loserscreen", new LoserScreen());
        map.put("matchmakingscreen", new MatchmakingScreen());
        map.put("winnerscreen", new winnerscreen());
        map.put("welcomescreen", new welcomescreen());
    }

    public static void main(String[] args) {
        //        sceneSwitch("welcomescreen");
        //        try {
        //            TimeUnit.SECONDS.sleep(10);
        //        }
        //        catch (Exception e){
        //            System.out.println("Error");
        //        }
        //
        //        sceneSwitch("gamescreen");
    }

    public static void sceneSwitch(String scene) {
        logger.info("switching scene to: {}", scene);
        frame.setSize(1077, 767);
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
//        Map<String, JPanel> map = new HashMap<String, JPanel>();
        CardLayout cardLayout = new CardLayout();



        panel.setLayout(cardLayout);
        panel.add(map.get("welcomescreen"), "welcomescreen");

        panel.add(map.get("matchmakingscreen"), "matchmakingscreen");
        panel.add(map.get("gamescreen"), "gamescreen");
        panel.add(map.get("winnerscreen"), "winnerscreen");
        panel.add(map.get("loserscreen"), "loserscreen");

        frame.setSize(1077, 805);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        cardLayout.show(panel, scene);
    }
}
