package com.github.TeamRocketBalleBalle.Ricktionary.Client;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Rectangle rectangle = new Rectangle(1077, 767);
        frame.setBounds(rectangle);
        frame.add(new GameScreen());
        frame.setVisible(true);
    }
}
/*
 TODO: Combining Different Scenes, Adding Some Functionality in some GUI Buttons, EndIng Scene (yeh Ayush and vatsal kar rhae h )
*/
