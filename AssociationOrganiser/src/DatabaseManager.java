import java.sql.*;
import java.util.ArrayList;

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
        expectedColumns.add("Name");
        expectedColumns.add("TeamName");

        name = "(\"" + name + "\")" + ";";

        String select = "select player.ID, player.Name, team.Name from player " +
                "inner join team on player.TeamID = team.ID where Name in " + name;

        ArrayList<String[]> players = executeQuery(select, expectedColumns);

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
        columns.add("Name");
        columns.add("TeamName");

        ID = "(\"" + ID + "\")" + ";";

        String select = "select player.ID, player.Name, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where TeamID in " + ID;

        ArrayList<String[]> players = executeQuery(select, columns);

        extractPlayersFromList(playerList, players);

        return playerList;
    }

    private static void extractPlayersFromList(ArrayList<Player> playerList, ArrayList<String[]> players) {
        for (String[] player : players) {

            int player_id = Integer.parseInt(player[0]);
            String player_name = player[1];
            String team_name = player[2];

            playerList.add(new Player(player_id, player_name, team_name));
        }
    }

    // TODO: add method to insert a new player into database
    public void addPlayerToDatabase(String player_name, int team_name) {

        // TODO: finish these queries for inserting players

        // first get team_id of the selected team_name
        // select ID from team where TeamName = '"team_name"'


        // then send an insert with the player_name and team_id
    }

    // TODO: add method to insert new team into database
    public void addTeamToDatabase(String team_name) {

    }

    // TODO: add method to fetch all teams and create / return fixtures using that data
    public void generateFixtures() {

    }

    // TODO: Test this function properly when we have actual queries implemented.
    public static void createTables() {

        ArrayList<String> queryList = new ArrayList<>();

        // create game table
        queryList.add("CREATE TABLE Game (\n" +
                        "GameID int NOT NULL AUTO_INCREMENT,\n" +
                        "HomeTeamScore int NOT NULL,\n" +
                        "AwayTeamScore int NOT NULL,\n" +
                        "WinnerID int NOT NULL,\n" +
                        "MatchID int NOT NULL,\n" +
                        "Played bool NOT NULL,\n" +
                        "CONSTRAINT Game_pk PRIMARY KEY (GameID)\n" +
                        ");");

        // create match table
        queryList.add("CREATE TABLE `Match` (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "HomePlayerID int NOT NULL,\n" +
                "AwayPlayerID int NOT NULL,\n" +
                "WinnerID int NOT NULL,\n" +
                "SetID int NOT NULL,\n" +
                "Played bool NOT NULL,\n" +
                "CONSTRAINT Match_pk PRIMARY KEY (ID)\n" +
                ");");

        // create player table
        queryList.add("CREATE TABLE Player (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20),\n" +
                "TeamID int NOT NULL,\n" +
                "CONSTRAINT Player_pk PRIMARY KEY (ID)\n" +
                ");");

        // create set table
        queryList.add("CREATE TABLE `Set` (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "MatchID int NOT NULL,\n" +
                "HomeTeamID int NOT NULL,\n" +
                "AwayTeamID int NOT NULL,\n" +
                "FinalScore int NOT NULL,\n" +
                "WinnerID int NOT NULL,\n" +
                "Played bool NOT NULL,\n" +
                "CONSTRAINT Set_pk PRIMARY KEY (ID)\n" +
                ");");

        // create team table
        queryList.add("CREATE TABLE Team (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20) NOT NULL,\n" +
                "CONSTRAINT Team_pk PRIMARY KEY (ID)\n" +
                ");");

        // Set up foreign keys

        queryList.add("ALTER TABLE Game ADD CONSTRAINT Game_Match FOREIGN KEY Game_Match (MatchID)\n" +
                "REFERENCES `Match` (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT Match_Set FOREIGN KEY Match_Set (SetID)\n" +
                "REFERENCES `Set` (ID);");

        queryList.add("ALTER TABLE Player ADD CONSTRAINT Player_Team FOREIGN KEY Player_Team (TeamID)\n" +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT Set_AwayTeam FOREIGN KEY Set_AwayTeam (AwayTeamID)\n" +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT Set_HomeTeam FOREIGN KEY Set_HomeTeam (HomeTeamID)\n" +
                "REFERENCES Team (ID);");


        try {

            Connection conn = openConnection();

            for (String aQueryList : queryList) {

                PreparedStatement preparedStatement = conn.prepareStatement(aQueryList);
                System.out.println(aQueryList);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /* QUERIES */


    // returns query from database in the form of a list of Player objects
    private static ArrayList<String[]> executeQuery(String query, ArrayList<String> expectedColumns) {

        // expectedColumns ArrayList allows us to make this data retrieval more generic, so we can retrieve any
        // data we like in the form of StringBuilders, rather than having to individually hardcode every query's
        // data retrieval.

        // TODO: the current usage of lists feels a bit clunky, maybe change it later?
        // LinkedList of LinkedLists
        ArrayList<String[]> rows = new ArrayList<>();

        try {
            Connection conn = openConnection();

            System.out.println("Sending query " + query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {

                String[] row = new String[expectedColumns.size()];

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