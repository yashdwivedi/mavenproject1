package com.github.TeamRocketBalleBalle.Ricktionary.Client;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenTest {

    @Test
    void displayTopThree() {
        new GameScreen();
        String output = GameScreen.displayTopThree(new AbstractMap.SimpleEntry("p1", 100), new AbstractMap.SimpleEntry("p2", 75), new AbstractMap.SimpleEntry("p3", 50));
        String expected = "<html>p1 : 100<br/>p2 : 75<br/>p3 : 50</html>";
        assertEquals(expected,output);
    }
}