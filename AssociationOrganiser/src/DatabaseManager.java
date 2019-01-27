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

    static StringBuilder getPlayerWithName(String name) {

        ArrayList<String> expectedColumns = new ArrayList<>();

        expectedColumns.add("ID");
        expectedColumns.add("PlayerName");
        expectedColumns.add("TeamName");

        name = "(\"" + name + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where PlayerName in " + name;

        return retrieveData(select, expectedColumns);
    }

    // TODO: think about making these return arrays? I'm hoping we don't have to mess around with tokenising later.
    static StringBuilder getPlayersWithTeamID(String ID) {

        ArrayList<String> columns = new ArrayList<>();

        columns.add("ID");
        columns.add("PlayerName");
        columns.add("TeamName");

        ID = "(\"" + ID + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where TeamID in " + ID;

        return retrieveData(select, columns);
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


    // returns query from database in the form of a linked list of the rows returned by DB query
    private static StringBuilder retrieveData(String query, ArrayList<String> expectedColumns) {

        // expectedColumns ArrayList allows us to make this data retrieval more generic, so we can retrieve any
        // data we like in the form of StringBuilders, rather than having to individually hardcode every query's
        // data retrieval.

        StringBuilder data = new StringBuilder();

        try {

            LinkedList<String> result = new LinkedList<String>();
            Connection conn = openConnection();

            System.out.println("Sending query " + query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {

                for (String expectedColumn : expectedColumns) {
                    data.append(rset.getString(expectedColumn));
                    data.append(" ");
                }

                data.append("\n");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}