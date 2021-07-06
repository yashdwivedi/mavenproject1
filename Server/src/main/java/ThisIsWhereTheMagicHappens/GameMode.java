package ThisIsWhereTheMagicHappens;

import com.github.TeamRocketBalleBalle.Ricktionary.Server.Player;
import com.github.TeamRocketBalleBalle.Ricktionary.Server.PlayersInput;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMode {

    public boolean ended() {
        return false;
    }

    public HashMap<Player, Integer> playTurn(ArrayList<PlayersInput> inputs){
        // big brain game logic
        HashMap<Player, Integer> playerIntegerHashMap = new HashMap<>();
        for (PlayersInput input :
                inputs) {
            playerIntegerHashMap.put(input.getThem(), (int) (Math.random() * 100));
        }
        return playerIntegerHashMap;
    }
}
