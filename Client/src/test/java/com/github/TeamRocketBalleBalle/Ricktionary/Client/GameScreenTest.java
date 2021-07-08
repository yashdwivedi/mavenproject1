package com.github.TeamRocketBalleBalle.Ricktionary.Client;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenTest {

    @Test
    void displayTopThree() {
        new GameScreen();
        ArrayList<Map.Entry<String, Integer>> inputs = new ArrayList<>();
        inputs.add(new AbstractMap.SimpleEntry("p1", 100));
        inputs.add(new AbstractMap.SimpleEntry("p2", 75));
        inputs.add(new AbstractMap.SimpleEntry("p3", 50));


        String output =
                GameScreen.displayTopThree(inputs);
        String expected = "<html>p1 : 100<br/>p2 : 75<br/>p3 : 50<br/></html>";
        assertEquals(expected, output);
    }
}
