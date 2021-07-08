package ThisIsWhereTheMagicHappens;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GameModeTest {

    @Test
    void playTurn() {}

    @ParameterizedTest
    @CsvSource({"theek,1", "answer,0"})
    void playTest(String input, int expectedScore) {
        GameMode g = new GameMode("answer");
        int output = g.play(input);
        assertEquals(expectedScore, output);
    }
}
