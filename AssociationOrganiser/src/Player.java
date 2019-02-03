// kill me what am i doing with my life REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

import java.util.ArrayList;

// TODO: finish Player class
// TODO: make better formatting for toString()
public class Player {

    private int player_id;
    private String player_name;
    private String team_name;

    @Override
    public String toString() {
        return "Player{" +
                "player_id=" + player_id +
                ", player_name='" + player_name + '\'' +
                ", team_name='" + team_name + '\'' +
                '}';
    }

    public Player(int player_id, String name, String team_name) {

        this.player_id = player_id;
        this.player_name = name;
        this.team_name = team_name;
    }

    /**********************************************STATIC METHODS******************************************************/

    private static ArrayList<Player> extractPlayersFromList(ArrayList<String[]> players) {

        ArrayList<Player> playerList = new ArrayList<>();
        for (String[] player : players) {

            playerList.add(new Player(Integer.parseInt(player[0]), player[1], player[2]));
        }
        return playerList;
    }

    static Player getPlayerWithName(String name) {

        name = "(\"" + name + "\")" + ";";

        String select = "SELECT Player.ID, Player.Name, Team.Name FROM Player " +
                "INNER JOIN Team ON Player.TeamID = Team.ID WHERE Player.Name = " + name;

        return extractPlayersFromList(DatabaseManager.executeQuery(select)).get(0);
    }

    static ArrayList<Player> getPlayersWithTeamName(String name) {

        name = "(\"" + name + "\")" + ";";

        String select = "SELECT Player.ID, Player.Name, Team.Name FROM Player INNER JOIN Team ON Player.TeamID = " +
                "Team.ID WHERE Team.Name = " + name;

        return extractPlayersFromList(DatabaseManager.executeQuery(select));
    }

    static void addPlayerToDatabase(String player_name, String team_name) {

        String insert = "INSERT INTO Player (Name, TeamID) VALUES (";

        insert += "\"" + player_name + "\"";
        insert += "," + " (" + "SELECT ID FROM Team WHERE Name = " + "\"" + team_name + "\"" + ")" + ")" + ";";

        DatabaseManager.insertData(insert);
    }

}
