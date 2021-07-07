package ThisIsWhereTheMagicHappens;

import com.github.TeamRocketBalleBalle.Ricktionary.Server.Player;
import com.github.TeamRocketBalleBalle.Ricktionary.Server.PlayersInput;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMode {
    private String answer;
    int multiplier;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public GameMode(String answer) {
        setAnswer(answer);
    }

    public boolean ended() {
        return false;
    }

    public HashMap<Player, Integer> playTurn(ArrayList<PlayersInput> inputs) {
        // big brain game logic
        HashMap<Player, Integer> playerIntegerHashMap = new HashMap<>();
        multiplier = 10;

        for (PlayersInput is : inputs) {
            playerIntegerHashMap.put(
                    is.getThem(),
                    play(
                            is
                                    .getTheirInput())); // TODO --> 2nd parameter will store the
                                                        // score of player
        }
        return playerIntegerHashMap;
    }

    public int play(String playerInput) {
        int score = 1;

        if (playerInput.equals(answer)) {

            score *= multiplier;
            if (multiplier >= 6) {
                multiplier -= 2;
            }
        }

        return score;
    }
}
