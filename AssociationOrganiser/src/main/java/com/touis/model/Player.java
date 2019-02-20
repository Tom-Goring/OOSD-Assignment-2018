package main.java.com.touis.model;

public class Player {

    private String playerName;

    public Player(String name) {

        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String toString() { return this.playerName; }
}