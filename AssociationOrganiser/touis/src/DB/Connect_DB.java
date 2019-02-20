package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_DB {

    private static Connection con = null;

    public static Connection getConnection() {

        if (con != null) return con;
        System.out.println("New connection obtained.");
        return getConnection(DatabaseManager.url, DatabaseManager.user, DatabaseManager.password);
    }

    private static Connection getConnection(String url, String user_name, String password) {

        try {

            con = DriverManager.getConnection(url, user_name, password);
        }
        catch (SQLException e) {e.printStackTrace();}

        return con;
    }
}
