package java.model;

import java.util.ArrayList;

public class Team {

    private String teamName;
    private ArrayList<Player> playerList;

    public Team(String teamName) {

        this.teamName = teamName;
        this.playerList = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getPlayerList() { return playerList; }

    public void addPlayer(Player player) { this.playerList.add(player); }

    public void removePlayer(Player playerToRemove) { this.playerList.remove(playerToRemove); }

    public String toString() { return this.teamName; }
}
