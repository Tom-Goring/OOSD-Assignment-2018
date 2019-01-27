import java.sql.*;

public class DatabaseManager {

    private static Connection openConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false";
        String user = "root";
        String password = "password";

        Connection conn;

        conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    static String getPlayerWithName(String name) {

        String result = "";

        name = "(\"" + name + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where PlayerName in "+name;

        result = retrieveIDPNTN(result, select);

        return result;
    }

    // these methods return strings so we can use the frontend to manipulate the data later.
    static String getPlayersWithTeamID(String ID) {

        String result = "";

        ID = "(\"" + ID + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where TeamID in "+ID;

        result = retrieveIDPNTN(result, select);

        return result;
    }

    // IDPNTM = ID PlayerName TeamName - will probably change this later (placeholder?)
    // should probably make this return a ResultSet to be operated upon - would be more
    // widely usable (plus not have a crazy name).
    private static String retrieveIDPNTN(String result, String select) {
        try {

            Connection conn = openConnection();

            System.out.println("Sending query " + select);

            PreparedStatement pstmt = conn.prepareStatement(select);

            ResultSet rset = pstmt.executeQuery();

            StringBuilder resultBuilder = new StringBuilder(result);
            while(rset.next()) {

                int PlayerID = rset.getInt("ID");
                String PlayerName = rset.getString("PlayerName");
                String TeamName = rset.getString("TeamName");

                resultBuilder.append(PlayerID).append(" ").append(PlayerName).append(" ").append(TeamName).append("\n");
            }
            result = resultBuilder.toString();

            conn.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
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
}
