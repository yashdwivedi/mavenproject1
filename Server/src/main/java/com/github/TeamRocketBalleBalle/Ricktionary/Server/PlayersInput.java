package com.github.TeamRocketBalleBalle.Ricktionary.Server;

public class PlayersInput {
    private Player whosInput;
    private String theirInput;
    private boolean specialMessage = false;

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

    public void setTheirInput(String theirInput) {
        this.theirInput = theirInput;
    }

    public void setSpecialMessage(boolean specialMessage) {
        this.specialMessage = specialMessage;
    }

    public boolean isSpecialMessage() {
        return specialMessage;
    }
}
