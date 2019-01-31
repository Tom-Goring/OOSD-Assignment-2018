import java.util.ArrayList;

public class Team {

    private int teamID;
    private String teamName;
    private ArrayList<Player> playerList;

    Team(int teamID, String teamName) {

        this.teamID = teamID;
        this.teamName = teamName;
        this.playerList = new ArrayList<>();
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
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

    public void updatePlayerList() {

        this.playerList = DatabaseManager.getPlayersWithTeamName(this.teamName);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
