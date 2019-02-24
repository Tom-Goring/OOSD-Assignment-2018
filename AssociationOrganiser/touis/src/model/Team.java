package model;

import java.util.ArrayList;

public class Team {

    final private String teamName;
    private ArrayList<Player> playerList;

    public Team(String teamName) {

        this.teamName = teamName;
        this.playerList = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public ArrayList<Player> getPlayerList() { return playerList; }

    public void addPlayer(Player player) { this.playerList.add(player); }

    public String toString() { return this.teamName; }
}
