package DB;

import java.util.ArrayList;

public class Team {

    private String teamName;
    private ArrayList<Player> playerList;

    // TODO: make this auto pull data? maybe add a constructor with an import option
    public Team(String teamName) {

        this.teamName = teamName;
        this.playerList = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return teamName;
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

    public static ArrayList<Team> getTeamList() {

        String select = "SELECT * FROM Team;";

        return extractTeamsFromList(DatabaseManager.executeQuery(select));
    }

    // TODO: add a delete team function (will also delete all related players?)
}
