package ThisIsWhereTheMagicHappens;

import com.github.TeamRocketBalleBalle.Ricktionary.Server.Player;
import com.github.TeamRocketBalleBalle.Ricktionary.Server.PlayersInput;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMode {
    private String answer;
    int multiplier;
    int imagesPlayed = 0;
    boolean nextImage = false;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public GameMode(String answer) {
        setAnswer(answer);
    }

    public boolean ended() {
        return imagesPlayed == 5;
    }

    public HashMap<Player, Integer> playTurn(ArrayList<PlayersInput> inputs) {
        // big brain game logic
        HashMap<Player, Integer> playerIntegerHashMap = new HashMap<>();
        multiplier = 10;
        boolean imageGuessed = false;

        for (PlayersInput is : inputs) {
            int roundScore = play(is.getTheirInput());
            playerIntegerHashMap.put(is.getThem(), roundScore);
            // if answer, modify the original input
            if (roundScore != 0) {
                is.setTheirInput(
                        is.getThem().getName()
                                + " has guessed the answer!\n\tNow guess this NEW ONE!");
                is.setSpecialMessage(true);
                imageGuessed = true;
            }
        }
        if (imageGuessed) {
            imagesPlayed++;
            setNextImage(true);
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

        return score == 1 ? 0 : score;
    }

    public boolean isNextImage() {
        return nextImage;
    }

    public void setNextImage(boolean nextImage) {
        this.nextImage = nextImage;
    }
}
