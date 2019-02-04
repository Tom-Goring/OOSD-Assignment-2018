package DB;

import java.util.ArrayList;

public class Team {

    private int teamID;
    private String teamName;
    private ArrayList<Player> playerList;

    // TODO: make this auto pull data? maybe add a constructor with an import option
    public Team(String teamName) {

        this.teamName = teamName;
        this.playerList = new ArrayList<>();
    }

    public int getTeamID() {
        return teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    public void addTeamToDatabase() {

        this.teamName = "(\"" + this.teamName + "\")" + ";";

        String insert = "INSERT INTO Team (Name) VALUES ";

        insert += this.teamName;

        DatabaseManager.insertData(insert);
    }

    /**********************************************STATIC METHODS******************************************************/

    private static ArrayList<Team> extractTeamsFromList(ArrayList<String[]> teams) {

        ArrayList<Team> teamList = new ArrayList<>();
        for (String[] team : teams) {

            teamList.add(new Team(team[1]));
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
}
