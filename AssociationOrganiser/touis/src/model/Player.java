package model;

public class Player {

    final private String playerName;

    public Player(String name) {

        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String toString() { return this.playerName; }
}