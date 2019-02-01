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

        this.playerList = Player.getPlayersWithTeamName(this.teamName);
    }

    private static ArrayList<Team> extractTeamsFromList(ArrayList<String[]> teams) {

        ArrayList<Team> teamList = new ArrayList<>();
        for (String[] team : teams) {

            teamList.add(new Team(Integer.parseInt(team[0]), team[1]));
        }
        return teamList;
    }

    static Team getTeamWithName(String name) {

        String select = "SELECT * FROM Team WHERE Name = " +  "\"" + name + "\"" + ";";

        return extractTeamsFromList(DatabaseManager.executeQuery(select)).get(0);
    }

    static ArrayList<Team> getTeamList() {

        String select = "SELECT * FROM Team;";

        return extractTeamsFromList(DatabaseManager.executeQuery(select));
    }

    static void addTeamToDatabase(String team_name) {

        team_name = "(\"" + team_name + "\")" + ";";

        String insert = "INSERT INTO Team (Name) VALUES ";

        insert += team_name;

        DatabaseManager.insertData(insert);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
