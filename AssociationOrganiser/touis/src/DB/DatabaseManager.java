// TODO: load players when loading teamList
// TODO: Update sets and games when updating match details

package DB;

import model.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseManager {

    static String url = "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false";
    static String user = "root";
    static String password = "password";

    private static User admin = createAdminDefault(); // for a main user admin to log into

    private static User createAdminDefault() {

        User admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("default");
        admin.setSalt(DB_Security.generateSalt());
        admin.setHashedPassword(DB_Security.hashPassword(admin.getPassword(), admin.getSalt()));
        return admin;
    }

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
                "Username varchar(20) UNIQUE,\n" +
                "PasswordSalt varbinary(128),\n" +
                "HashedPassword varbinary(128)," +
                "PrivilegeLevel int DEFAULT 1," +
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

        // DB_Match Table FKs

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

        // DB_Player Table FKs

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

            // create tables
            for (String query : queryList) {

                System.out.println(query);
                Statement.addBatch(query);
            }

            Statement.executeBatch();

            // insert default admin account
            String insertAdmin = "INSERT INTO User (Username, PasswordSalt, HashedPassword, PrivilegeLevel) VALUES (?, ?, ?, ?);";
            PreparedStatement insert = Connect_DB.getConnection().prepareStatement(insertAdmin);
            insert.setString(1, admin.getUsername());
            insert.setBytes(2, admin.getSalt());
            insert.setBytes(3, admin.getHashedPassword());
            insert.setInt(4, 2); // in prep for admins having more power than users

            insert.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static class DB_Security {

        public static byte[] generateSalt() {

            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[32];
            random.nextBytes(bytes); // fills bytes array with random bytes
            return bytes;
        }

        public static byte[] hashPassword(String password, byte[] salt) {
            try {

                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 25000, 256);
                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

                try {

                    return skf.generateSecret(spec).getEncoded();
                }
                catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static boolean checkPassword(String passwordToCheck, User user) {

            // hash entered password with desired user account salt
            byte[] hashAttempt = DB_Security.hashPassword(passwordToCheck, user.getSalt());

            // compare hash of entered to pre-existing hash
            if (Arrays.equals(hashAttempt, user.getHashedPassword())) {

                return true;
            }
            else {

                return false;
            }
        }
    }

    public static class DB_User {

        public static User getUserFromDatabase(String username) {

            String query = "SELECT * FROM User WHERE (Username = ?)";

            try {

                PreparedStatement getUser = Connect_DB.getConnection().prepareStatement(query);
                getUser.setString(1, username);
                ResultSet rset = getUser.executeQuery();

                rset.next();

                if (rset.getInt("PrivilegeLevel") == 1) {

                    User user = new User();
                    user.setUsername(rset.getString("Username"));
                    user.setSalt(rset.getBytes("PasswordSalt"));;
                    user.setHashedPassword(rset.getBytes("HashedPassword"));
                    return user;
                }
                else if (rset.getInt("PrivilegeLevel") == 2) {

                    User user = new Admin();
                    user.setUsername(rset.getString("Username"));
                    user.setSalt(rset.getBytes("PasswordSalt"));;
                    user.setHashedPassword(rset.getBytes("HashedPassword"));
                    return user;
                }
                else {
                    return null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static ArrayList<User> getUserListFromDatabase() {

            ArrayList<User> userList = new ArrayList<>();

            String query = "SELECT Username, PrivilegeLevel FROM User;";

            try {

                PreparedStatement getUserList = Connect_DB.getConnection().prepareStatement(query);
                System.out.println(getUserList);
                ResultSet rset = getUserList.executeQuery();

                while (rset.next()) {

                    if (rset.getInt("PrivilegeLevel") == 1) {
                        User user = new User();
                        user.setUsername(rset.getString("Username"));
                        userList.add(user);
                    }
                    else if (rset.getInt("PrivilegeLevel") == 2) {

                        User admin = new Admin();
                        admin.setUsername(rset.getString("Username"));
                        userList.add(admin);
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            return userList;
        }

        public static boolean addPlayerToDatabase(User user) {

            String query = "INSERT INTO User (Username, PasswordSalt, HashedPassword) VALUES (?, ?, ?);";

            try {

                PreparedStatement addNewUser = Connect_DB.getConnection().prepareStatement(query);
                addNewUser.setString(1, user.getUsername());
                addNewUser.setBytes(2, user.getSalt());
                addNewUser.setBytes(3, user.getHashedPassword());

                System.out.println(addNewUser);
                addNewUser.executeUpdate();
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                   return false;
                }
                else {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }

        public static boolean changeUserPrivilegeLevel(User user, int privilegeToSet) {

            String update = "UPDATE User SET PrivilegeLevel = ? WHERE Username = ?;";

            try {

                PreparedStatement updatePrivilegeLevel = Connect_DB.getConnection().prepareStatement(update);
                updatePrivilegeLevel.setInt(1, privilegeToSet);
                updatePrivilegeLevel.setString(2, user.getUsername());
                System.out.println(updatePrivilegeLevel);
                updatePrivilegeLevel.executeUpdate();
                return true;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class DB_Team {

        static public void addNewTeamToDatabase(Team team) {

            String insert = "INSERT INTO Team (Name) VALUES (?);";

            try {

                PreparedStatement insertTeam = Connect_DB.getConnection().prepareStatement(insert);
                insertTeam.setString(1, team.getTeamName());
                System.out.println(insertTeam);
                insertTeam.executeUpdate();
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    System.out.println("ERROR: DB_Team name \""+ team.getTeamName() +"\" already present.");
                }
                else {
                    e.printStackTrace();
                }
            }
        }


        static Team getTeamFromDatabase(String teamName) {

            String query = "SELECT Player.Name FROM Player WHERE (TeamID = (SELECT ID FROM team WHERE team.Name = ?));";

            try {

                PreparedStatement selectTeam = Connect_DB.getConnection().prepareStatement(query);
                selectTeam.setString(1, teamName);

                System.out.println(selectTeam);
                ResultSet rset = selectTeam.executeQuery();

                Team team = new Team(teamName);

                while (rset.next()) {

                    team.getPlayerList().add(new Player(rset.getString("Name")));
                }

                return team;
            }
            catch (SQLException e) {e.printStackTrace();}
            return null;
        }

        public static ArrayList<Team> getTeamListFromDatabase() {

            ArrayList<Team> teamList = new ArrayList<>();
            String query = "SELECT Name FROM team";

            try {

                PreparedStatement getTeamNames = Connect_DB.getConnection().prepareStatement(query);
                System.out.println(getTeamNames);
                ResultSet rset = getTeamNames.executeQuery();

                while (rset.next()) {

                    teamList.add(getTeamFromDatabase(rset.getString("Name")));
                }

                return teamList;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class DB_Player {

        static void addNewPlayerToDatabase(Player player, Team team) {

            String insert = "INSERT INTO Player (Name, TeamID) VALUES (?, (SELECT ID FROM Team WHERE Name = ?));";

            try {

                PreparedStatement insertPlayer = Connect_DB.getConnection().prepareStatement(insert);
                insertPlayer.setString(1, player.getPlayerName());
                insertPlayer.setString(2, team.getTeamName());
                System.out.println(insertPlayer);
                insertPlayer.executeUpdate();
            }
            catch (SQLException e) {e.printStackTrace();}
        }

        static ArrayList<Player> getPlayerList() {

            ArrayList<Player> players = new ArrayList<>();

            String query = "SELECT Name FROM player;";

            try {

                PreparedStatement getPlayers = Connect_DB.getConnection().prepareStatement(query);
                ResultSet rset = getPlayers.executeQuery();

                while(rset.next()) {

                    players.add(new Player(rset.getString("DB_Player.Name")));
                }

                return players;

            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class DB_Match {

        public static void sendNewMatchToDB(Match match) {

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
        static void updateMatchInformation(Match match) {

            // TODO: look at pushing match updates
            String update = "UPDATE `Match` " +
                    "SET " +
                    "HomePlayer1ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "HomePlayer2ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "AwayPlayer1ID = (SELECT ID FROM player WHERE Name = ?), " +
                    "AwayPlayer2ID = (SELECT ID FROM player WHERE Name = ?)," +
                    "WinnerID = (SELECT ID FROM team WHERE Name = ?);";

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