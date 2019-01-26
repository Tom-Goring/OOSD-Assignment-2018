import java.sql.*;

// kill me what am i doing with my life REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

public class Player {

    private int player_id;
    private String player_name;
    private int team_id;

    //maybe a constructor that auto pulls a player with just the id? or an id with a name?
    public Player(int id, String name, int team_id) {

        this.player_id = id;
        this.player_name = name;
        this.team_id = team_id;
    }

    public Player(String name, int team_id) {

        this.player_name = name;
        this.team_id = team_id;
    }

    // method to send an instance of player to SQL server?
    public void addPlayerToDatabase() {

        String sql = "insert into player(PlayerName, TeamID) values(?, ?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false", "root", "password");

             PreparedStatement pstmt = conn.prepareStatement(sql); ) {  // end try

            pstmt.setString(1, player_name);
            pstmt.setInt(2, player_id);

            pstmt.executeUpdate();

            String select = "select * from player";

            PreparedStatement slt = conn.prepareStatement(select);

            slt.executeQuery();

        } catch(SQLException ex) {

            ex.printStackTrace();
        }
    }

    // this all turned out to be more complicated than I thought
    public static String getPlayerWithName(String name) {

        StringBuilder string = new StringBuilder();

        name = "(\"" + name + "\")";

        String select = "select ID, PlayerName, TeamID from player";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false", "root", "password");

             PreparedStatement pstmt = conn.prepareStatement(select); ) {

            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {

                int PlayerID = rset.getInt("ID");
                String PlayerName = rset.getString("PlayerName");
                int TeamID = rset.getInt("TeamID");

                string.append(PlayerID).append(" ").append(PlayerName).append(" ").append(TeamID).append("\n");
            }

        }
        catch (SQLException ex) {

            ex.printStackTrace();
        }

        return string.toString();
    }
}
