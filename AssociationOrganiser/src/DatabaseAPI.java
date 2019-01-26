import java.sql.*;

public class DatabaseAPI {

    // this all turned out to be more complicated than I thought
    public static String getPlayerWithName(String name) {

        StringBuilder string = new StringBuilder();

        name = "(\"" + name + "\")" + ";";

        String select = "select player.ID, player.PlayerName, team.TeamName from player " +
                "inner join team on player.TeamID = team.ID where PlayerName in "+name;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false", "root", "password");

             PreparedStatement pstmt = conn.prepareStatement(select); ) {

            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {

                int PlayerID = rset.getInt("ID");
                String PlayerName = rset.getString("PlayerName");
                String TeamName = rset.getString("TeamName");

                string.append(PlayerID).append(" ").append(PlayerName).append(" ").append(TeamName).append("\n");
            }

        }
        catch (SQLException ex) {

            ex.printStackTrace();
        }

        return string.toString();
    }

    // TODO: finish this lol
    public static void addPlayerToDatabase(String player_name, int team_id) {

        String sql = "insert into player(PlayerName, TeamID) values(?, ?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tournament?allowPublicKeyRetrieval=true&&useSSL=false", "root", "password");

             PreparedStatement pstmt = conn.prepareStatement(sql); ) {  // end try

            pstmt.setString(1, player_name);
            pstmt.setInt(2, team_id);

            pstmt.executeUpdate();

            String select = "select * from player";

            PreparedStatement slt = conn.prepareStatement(select);

            slt.executeQuery();

        } catch(SQLException ex) {

            ex.printStackTrace();
        }
    }
}
