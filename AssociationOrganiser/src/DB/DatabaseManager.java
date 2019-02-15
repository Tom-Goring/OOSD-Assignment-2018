package DB;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    static String url = "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false";
    static String user = "root";
    static String password = "password";

    // TODO: consider moving each table query into the relevant class?
    // TODO: test to make sure that database structure satisfies requirements
    public static void createTables() {

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
                "HashedPassword varchar(128)," +
                "CONSTRAINT User_pk PRIMARY KEY (ID)\n" +
                ");");

        // create player table
        queryList.add("CREATE TABLE Player (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20) UNIQUE ,\n" +
                "TeamID int NOT NULL,\n" +
                "CONSTRAINT Player_pk PRIMARY KEY (ID)\n" +
                ");");

        // create team table
        queryList.add("CREATE TABLE Team (\n" +
                "ID int NOT NULL AUTO_INCREMENT,\n" +
                "Name varchar(20) NOT NULL UNIQUE,\n" +
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

            Statement Statement = Connect_DB.getConnection().createStatement();

            for (String query : queryList) {

                System.out.println(query);
                Statement.addBatch(query);
            }

            Statement.executeBatch();


        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    static class User {

        public static DB.User loadUserUsingUsername(String username) {

            String query = "SELECT * FROM User WHERE (Username = ?)";

            try {

                PreparedStatement selectUser = Connect_DB.getConnection().prepareStatement(query);
                selectUser.setString(1, username);
                ResultSet rset = selectUser.executeQuery();

                rset.next();

                DB.User user = new DB.User();

                user.setUsername(rset.getString("Username"));
                user.setSalt(rset.getString("PasswordSalt"));;
                user.setHashedPassword(rset.getString("HashedPassword"));
                return user;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void sendNewUserToDB(DB.User user) {

            String query = "INSERT INTO User (Username, PasswordSalt, HashedPassword) VALUES (?, ?, ?);";

            try {

                PreparedStatement addNewUser = Connect_DB.getConnection().prepareStatement(query);
                addNewUser.setString(1, user.getUsername());
                addNewUser.setString(2, user.getSalt());
                addNewUser.setString(3, user.getHashedPassword());
            }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }

    static class Team {

        static public void insertTeam(DB.Team team) {

            String insert = "INSERT INTO Team (Name) VALUES (?);";

            try {

                PreparedStatement insertTeam = Connect_DB.getConnection().prepareStatement(insert);
                insertTeam.setString(1, team.getTeamName());
                System.out.println(insertTeam);
                insertTeam.executeUpdate();
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    System.out.println("ERROR: Team name \""+ team.getTeamName() +"\" already present.");
                }
                else {
                    e.printStackTrace();
                }
            }
        }

        // TODO: load players too
        static DB.Team loadTeamInformation(String teamName) {

            String query = "SELECT * FROM Team WHERE (Name = ?);";

            try {

                PreparedStatement selectTeam = Connect_DB.getConnection().prepareStatement(query);
                selectTeam.setString(1, teamName);
                System.out.println(selectTeam);
                ResultSet rset = selectTeam.executeQuery();

                rset.next();

                return new DB.Team(rset.getString("Name"));
            }
            catch (SQLException e) {e.printStackTrace();}
            return null;
        }

        static ArrayList<DB.Team> getTeamList() {

            ArrayList<DB.Team> teamList = new ArrayList<>();
            String query = "SELECT * FROM team";

            try {

                PreparedStatement getTeamList = Connect_DB.getConnection().prepareStatement(query);
                System.out.println(getTeamList);
                ResultSet rset = getTeamList.executeQuery();

                while (rset.next()) {

                    teamList.add(new DB.Team(rset.getString("Name")));
                }

                return teamList;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static class Player {

        static void sendNewPlayerToDB(DB.Player player) {

            String insert = "INSERT INTO Player (Name, TeamID) VALUES (?, (SELECT ID FROM Team WHERE Name = ?));";

            try {

                PreparedStatement insertPlayer = Connect_DB.getConnection().prepareStatement(insert);
                insertPlayer.setString(1, player.getPlayerName());
                insertPlayer.setString(2, player.getTeamName());
                System.out.println(insertPlayer);
                insertPlayer.executeUpdate();
            }
            catch (SQLException e) {e.printStackTrace();}
        }

        static DB.Player loadPlayerInformation(String playerName) {

            String query = "SELECT Player.ID, Player.Name, Team.Name FROM Player INNER JOIN Team ON Player.TeamID = Team.ID WHERE Player.Name = ?";

            try {

                PreparedStatement findPlayer = Connect_DB.getConnection().prepareStatement(query);
                findPlayer.setString(1, playerName);
                System.out.println(findPlayer);
                ResultSet rset = findPlayer.executeQuery();

                rset.next();

                return new DB.Player(playerName, new DB.Team(rset.getString("Name")));
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static class Match {

        static void sendNewMatchToDB(DB.Match match) {

            String insertMatch = "INSERT INTO `Match` (HomeTeamID, AwayTeamID) VALUES (" +
                    "(SELECT ID FROM team WHERE Name = ?), " +
                    "(SELECT ID FROM team WHERE Name = ?));";

            String insertSet = "INSERT INTO `Set` (SetNumber, MatchID) VALUES (?, " +
                    "(SELECT ID FROM `match` " +
                    "WHERE " +
                    "HomeTeamID = (SELECT ID FROM team WHERE Name = ?) " +
                    "AND " +
                    "AwayTeamID = (SELECT ID FROM team WHERE Name = ?)))";

            // Gets SetID by finding all sets from the match, then picks the ID from those using setNumber
            String insertGame = "INSERT INTO `game` (GameNumber, SetID) VALUES " +
                    "(?, " +
                    "(SELECT ID FROM `set` " +
                    "WHERE MatchID = " +
                    "(SELECT ID FROM `match` " +
                    "WHERE HomeTeamID = (SELECT ID FROM team WHERE Name = ?) " +
                    "AND " +
                    "AwayTeamID = (SELECT ID FROM team WHERE Name = ?)) " +
                    "AND " +
                    "SetNumber = ?))";

            try {

                PreparedStatement insert = Connect_DB.getConnection().prepareStatement(insertMatch);
                insert.setString(1, match.getHomeTeam().getTeamName());
                insert.setString(2, match.getAwayTeam().getTeamName());

                System.out.println(insert);
                insert.executeUpdate();

                // add set details to set table

                insert = Connect_DB.getConnection().prepareStatement(insertSet);

                for (int i = 0; i < 5; i++) {

                    insert.setInt(1, i + 1);
                    insert.setString(2, match.getHomeTeam().getTeamName());
                    insert.setString(3, match.getAwayTeam().getTeamName());
                    insert.addBatch();
                }

                insert.executeBatch();

                // add game details to game table
                insert = Connect_DB.getConnection().prepareStatement(insertGame);

                for (int setNumber = 1; setNumber < 6; setNumber++) {

                    for (int gameNumber = 1; gameNumber < 4; gameNumber++) {

                        insert.setInt(1, gameNumber);
                        insert.setString(2, match.getHomeTeam().getTeamName());
                        insert.setString(3, match.getAwayTeam().getTeamName());
                        insert.setInt(4, setNumber);
                        insert.addBatch();
                    }
                }

                insert.executeBatch();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function assumes the match has been played and all data is available
        static void updateMatchInformation(DB.Match match) {

            // List of information to send:
                // HP1
                // HP2
                // AP1
                // AP2
                // Winner
                // Sets
                    // Games

            String update = "UPDATE `Match` " +
                    "SET " +
                    "HomePlayer1ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "HomePlayer2ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "AwayPlayer1ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "AwayPlayer2ID = (SELECT ID FROM player WHERE Name = ?)," +
                    "WinnerID = (SELECT ID FROM team WHERE Name = ?);";

            // TODO: make this update sets and games
            try {

                PreparedStatement updateMatch = Connect_DB.getConnection().prepareStatement(update);
                updateMatch.setString(1, match.getHomeTeamPlayer1().getPlayerName());
                updateMatch.setString(2 , match.getHomeTeamPlayer2().getPlayerName());
                updateMatch.setString(3 , match.getAwayTeamPlayer1().getPlayerName());
                updateMatch.setString(4 , match.getAwayTeamPlayer1().getPlayerName());
                updateMatch.setString(5, match.getWinningTeam().getTeamName());

                System.out.println(updateMatch);
                updateMatch.executeUpdate();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}