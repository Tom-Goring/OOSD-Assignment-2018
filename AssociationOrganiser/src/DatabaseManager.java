import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DatabaseManager {

    private static Connection openConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false";
        String user = "root";
        String password = "password";

        Connection conn;

        conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    static ArrayList<Player> getPlayerWithName(String name) {

        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<String> expectedColumns = new ArrayList<>();

        expectedColumns.add("ID");
        expectedColumns.add("PlayerName");
        expectedColumns.add("TeamName");

        name = "(\"" + name + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where PlayerName in " + name;

        LinkedList<String[]> players = retrieveData(select, expectedColumns);

        extractPlayersFromList(playerList, players);

        return playerList;
    }

    // TODO: think about making these return arrays? I'm hoping we don't have to mess around with tokenising later.
    // TODO: look at interacting with player class here
    // Thinking about it, we almost certainly will. Goddamit.
    static ArrayList<Player> getPlayersWithTeamID(String ID) {

        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<String> columns = new ArrayList<>();

        columns.add("ID");
        columns.add("PlayerName");
        columns.add("TeamName");

        ID = "(\"" + ID + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where TeamID in " + ID;

        LinkedList<String[]> players = retrieveData(select, columns);

        extractPlayersFromList(playerList, players);

        return playerList;
    }

    private static void extractPlayersFromList(ArrayList<Player> playerList, LinkedList<String[]> players) {
        for (String[] player : players) {

            int player_id = Integer.parseInt(player[0]);
            String player_name = player[1];
            String team_name = player[2];

            playerList.add(new Player(player_id, player_name, team_name));
        }
    }

    // TODO: add method to insert a new player into database
    public void addPlayerToDatabase(String player_name, int team_id) {

    }

    // TODO: add method to insert new team into database
    public void addTeamToDatabase(String team_name) {

    }

    // TODO: add method to fetch all teams and create / return fixtures using that data
    public void generateFixtures() {

    }

    // TODO: finish method to create tables in case of disaster (and also allowing us both to have the same DB schema).
    public static void createTables() {

        // create team table
        String createTeam = "create table team (" +
                "ID int unsigned primary key," +
                "TeamName varchar(20));";

        // create player table
        String createPlayer = "create table player (" +
                "ID int unsigned primary key," +
                "PlayerName varchar(20)," +
                "TeamID int unsigned," +
                "foreign key (TeamID) references team(ID);";

        // create match table
        String createMatch = "create table match (" +
                "ID int unsigned primary key," +
                "HomeTeamID int unsigned," +
                "AwayTeamID int unsigned," +
                "WinnerID int unsigned)," +
                "foreign key (HomeTeamID) references team(ID)," +
                "foreign key (AwayTeamID) references team(ID)," +
                "foreign key (WinnerID) references team(ID));";

        // create set table
        String createSet = "create table set (" +
                "";

        // create game table
    }

    /* QUERIES */


    // returns query from database in the form of a list of Player objects
    private static LinkedList<String[]> retrieveData(String query, ArrayList<String> expectedColumns) {

        // expectedColumns ArrayList allows us to make this data retrieval more generic, so we can retrieve any
        // data we like in the form of StringBuilders, rather than having to individually hardcode every query's
        // data retrieval.

        // TODO: the current usage of lists feels a bit clunky, maybe change it later?
        // LinkedList of LinkedLists
        LinkedList<String[]> rows = new LinkedList<>();

        try {
            Connection conn = openConnection();

            System.out.println("Sending query " + query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {

                String[] row = new String[3];

                for (int i = 0; i < expectedColumns.size(); i++) {

                    row[i] = rset.getString(expectedColumns.get(i));
                }

                rows.add(row);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }
}