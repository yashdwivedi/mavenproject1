package com.github.TeamRocketBalleBalle.Ricktionary.Client;

import javax.swing.*;
import java.awt.*;

public class App {



    public static void main(String[] args) {
        // Don't Know What to Write Here... @RoguedBear
    }
    public static void switchScene(Component scene)
    {
        JFrame frame = new JFrame();
        Rectangle rectangle = new Rectangle(1077, 767);
        frame.setBounds(rectangle);
        frame.getContentPane().removeAll();
        frame.add(scene);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}
