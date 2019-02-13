package DB;

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

    // TODO: consider moving each table query into the relevant class?
    // TODO: test to make sure that database structure satisfies requirements
    static void createTables() {

        ArrayList<String> queryList = new ArrayList<>();

        queryList.add("SET FOREIGN_KEY_CHECKS = 0");
        queryList.add("DROP TABLE if exists User;");
        queryList.add("DROP TABLE if exists Player;");
        queryList.add("DROP TABLE if exists Game;");
        queryList.add("DROP TABLE if exists `Match`");
        queryList.add("DROP TABLE if exists `Set`;");
        queryList.add("DROP TABLE if exists Team");
        queryList.add("SET FOREIGN_KEY_CHECKS = 1");

        queryList.add("CREATE TABLE User (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Username varchar(20),\n" +
                "PasswordSalt varchar(128),\n" +
                "HashedPassword varchar(128)" +
                "CONSTRAINT User_pk PRIMARY KEY (ID)\n" +
                ");");

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
                "HomeTeamID int NOT NULL,\n" +
                "AwayTeamID int NOT NULL,\n" +
                "HomePlayer1ID int,\n" +
                "HomePlayer2ID int,\n" +
                "AwayPlayer1ID int,\n" +
                "AwayPlayer2ID int,\n" +
                "WinnerID int,\n" +
                "CONSTRAINT Match_pk PRIMARY KEY (ID)\n" +
                ");");

        // create set table
        queryList.add("CREATE TABLE `Set` (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "SetNumber int NOT NULL,\n" +
                "MatchID int NOT NULL,\n" +
                "HomePlayerID int,\n" +
                "AwayPlayerID int,\n" +
                "FinalScore int,\n" +
                "WinnerID int,\n" +
                "CONSTRAINT Set_pk PRIMARY KEY (ID)\n" +
                ");");

        // TODO: consider moving game/set/match information into a separate 1-1 table containing information.

        // create game table
        queryList.add("CREATE TABLE Game (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "GameNumber int NOT NULL,\n" +
                "HomeTeamScore int,\n" +
                "AwayTeamScore int,\n" +
                "WinnerID int,\n" +
                "SetID int NOT NULL,\n" +
                "CONSTRAINT Game_pk PRIMARY KEY (ID)\n" +
                ");");

        // Set up foreign keys

        //queryList.clear();

        // Game Table FKs
        queryList.add("ALTER TABLE Game ADD CONSTRAINT Game_Match FOREIGN KEY Game_Match (SetID) " +
                "REFERENCES `Set` (ID);");

        queryList.add("ALTER TABLE Game ADD CONSTRAINT Winner_Team FOREIGN KEY Winner_Team (WinnerID) " +
                "REFERENCES Team (ID);");

        // Match Table FKs

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT Match_HomeTeam FOREIGN KEY Match_HomeTeam (HomeTeamID) " +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Match` ADD CONSTRAINT Match_AwayTeam FOREIGN KEY Match_AwayTeam (AwayTeamID) " +
                "REFERENCES Team (ID);");

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

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT PIDHT FOREIGN KEY PIDHT (HomePlayerID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT PIDAT FOREIGN KEY PIDAT (AwayPlayerID) " +
                "REFERENCES Player (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT WinnerID_TeamID FOREIGN KEY WinnerID_TeamID (WinnerID) " +
                "REFERENCES Team (ID);");

        queryList.add("ALTER TABLE `Set` ADD CONSTRAINT SetMatchID_Match FOREIGN KEY (MatchID) REFERENCES `Match` (ID);");


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

    static String surroundWithQuotes(String string) {

        return "\"" + string + "\"";
    }

    static ArrayList<String[]> executeQuery(String query) {

        ArrayList<String[]> table = new ArrayList<>();

        try {
            Connection conn = openConnection();

            System.out.println("Sending query " + query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            ResultSetMetaData rsetMeta = rset.getMetaData();
            int columnCount = rsetMeta.getColumnCount();

            while (rset.next()) {

                String[] row = new String[columnCount];

                for (int i = 0; i < columnCount; i++) {

                    row[i] = rset.getString(i+1);
                }

                table.add(row);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return table;
    }

    // use this to send data to the server: update = SQL command
    static void insertData(String update) {

        try {

            Connection conn = openConnection();

            System.out.println("Executing command: " + update);

            PreparedStatement prep = conn.prepareStatement(update);

            prep.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}