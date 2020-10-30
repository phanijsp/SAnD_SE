
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class ConnectionManager {
    static String ip = "";
    static String username = "";
    static String password = "";
    static String dbName = "";
    public ConnectionManager(String ip, String username, String password, String dbName) {

        ConnectionManager.ip = ip;
        ConnectionManager.username = username;
        ConnectionManager.password = password;
        ConnectionManager.dbName = dbName;
    }
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("\nLoaded jdbc driver!");
            String connectionUrl = "jdbc:mysql://"+ip+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8&user="+username+"&password="+password;
            conn = DriverManager.getConnection(connectionUrl);
            System.out.println("\nConnected to mysql server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("\nCan't find jdbc driver!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\nFailed to connect to mysql server!");

        }
        return conn;
    }

}
