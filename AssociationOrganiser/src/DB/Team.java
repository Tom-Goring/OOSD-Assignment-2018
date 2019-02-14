package DB;

import java.util.ArrayList;

public class Team {

    private String teamName;
    private ArrayList<Player> playerList;

    // TODO: add second constructor + playerlist?
    public Team(String teamName) {

        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}
