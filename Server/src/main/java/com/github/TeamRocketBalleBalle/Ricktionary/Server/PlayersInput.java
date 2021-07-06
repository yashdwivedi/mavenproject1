package com.github.TeamRocketBalleBalle.Ricktionary.Server;

public class PlayersInput {
    private Player whosInput;
    private String theirInput;

    public PlayersInput(Player whosInput, String theirInput) {
        this.whosInput = whosInput;
        this.theirInput = theirInput;
    }

    public Player getThem() {
        return whosInput;
    }

    public String getTheirInput() {
        return theirInput;
    }
}
