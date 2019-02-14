package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int ID;
    private String username;
    private String password;
    private String salt;
    private String hashedPassword;

    public void getUser(String enteredUsername) {

        Connection conn = null;
        String query = "SELECT * FROM User WHERE (Username = ?)";

        try {

            PreparedStatement selectUser = Connect_DB.getConnection().prepareStatement(query);
            selectUser.setString(1, enteredUsername);
            ResultSet rset = selectUser.executeQuery();

            while (rset.next()) {

                this.ID = rset.getInt("ID");
                this.username = rset.getString("Username");
                this.salt = rset.getString("PasswordSalt");
                this.hashedPassword = rset.getString("HashedPassword");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public void addNewUser(String username, String password) {

        Connection conn = null;
        String salt = "";
        String query = "INSERT INTO User VALUES (?, ?, ?);";

        try {

            PreparedStatement addNewUser = Connect_DB.getConnection().prepareStatement(query);
            addNewUser.setString(1, username);
            addNewUser.setString(2, salt);
            addNewUser.setString(3, hashedPassword);
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
