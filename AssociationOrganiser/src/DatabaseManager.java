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

        // create player table
        queryList.add("CREATE TABLE Player (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20),\n" +
                "TeamID int NOT NULL,\n" +
                "CONSTRAINT Player_pk PRIMARY KEY (ID)\n" +
                ");");

        // create team table
        queryList.add("CREATE TABLE Team (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20) NOT NULL,\n" +
                "CONSTRAINT Team_pk PRIMARY KEY (ID)\n" +
                ");");

        // create match table
        queryList.add("CREATE TABLE `Match` (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "HomePlayer1ID int NOT NULL,\n" +
                "HomePlayer2ID int NOT NULL,\n" +
                "AwayPlayer1ID int NOT NULL,\n" +
                "AwayPlayer2ID int NOT NULL,\n" +
                "WinnerID int NOT NULL,\n" +
                "Played bool NOT NULL,\n" +
                "CONSTRAINT Match_pk PRIMARY KEY (ID)\n" +
                ");");

        // create set table
        queryList.add("CREATE TABLE `Set` (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "MatchID int NOT NULL,\n" +
                "HomeTeamID int NOT NULL,\n" +
                "AwayTeamID int NOT NULL,\n" +
                "PlayerIDForHomeTeam int NOT NULL,\n" +
                "PlayerIDForAwayTeam int NOT NULL,\n" +
                "FinalScore int NOT NULL,\n" +
                "WinnerID int NOT NULL,\n" +
                "Played bool NOT NULL,\n" +
                "CONSTRAINT Set_pk PRIMARY KEY (ID)\n" +
                ");");

        // TODO: consider moving game/set/match information into a seperate 1-1 table containing information.

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

        // Set up foreign keys

        // Game Table FKs
        queryList.add("ALTER TABLE Game ADD CONSTRAINT Game_Match FOREIGN KEY Game_Match (MatchID) " +
                "REFERENCES `Match` (ID);");

        queryList.add("ALTER TABLE Game ADD CONSTRAINT Winner_Team FOREIGN KEY Winner_Team (WinnerID) " +
                "REFERENCES `Team` (ID);");

        // Match Table FKs

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT HPID1_Player FOREIGN KEY HPID1_Player (HomePlayer1ID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT HPID2_Player FOREIGN KEY HPID2_Player (HomePlayer2ID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT APID1_Player FOREIGN KEY APID1_Player (AwayPlayer1ID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT APID2_Player FOREIGN KEY APID2_Player (AwayPlayer2ID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT MatchWinner_Team FOREIGN KEY MatchWinner_Team (WinnerID) " +
                "REFERENCES Team (ID);");

        // Player Table FKs

        queryList.add("ALTER TABLE Player ADD CONSTRAINT Player_Team FOREIGN KEY Player_Team (TeamID) " +
                "REFERENCES Team (ID);");

        // Set Table FKs

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT Set_AwayTeam FOREIGN KEY Set_AwayTeam (AwayTeamID) " +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT Set_HomeTeam FOREIGN KEY Set_HomeTeam (HomeTeamID) " +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT PIDHT FOREIGN KEY PIDHT (PlayerIDForHomeTeam) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT PIDAT FOREIGN KEY PIDAT (PlayerIDForAwayTeam) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT WinnerID_TeamID FOREIGN KEY WinnerID_TeamID (WinnerID) " +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT SetMatchID_Match FOREIGN KEY (MatchID) REFERENCES `match` (ID);");


        try {


            Connection conn = openConnection();

            /*
            PreparedStatement prep = conn.prepareStatement(queryList.get(queryList.size()-1));

            prep.executeUpdate();*/


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