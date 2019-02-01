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

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

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

        String getID = "select * from Team where Name = " + "\"" + team_name + "\"";

        ArrayList<String[]> data = DatabaseManager.executeQuery(getID);

        String ID = data.get(0)[0];

        String insert = "INSERT INTO Player (Name, TeamID) VALUES (";

        insert += "\"" + player_name + "\"";
        insert += ", " + ID + ")";

        DatabaseManager.insertData(insert);
    }

}
