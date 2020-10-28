
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class ConnectionManager {
    static JTextArea log;
    static String ip = "";
    static String username = "";
    static String password = "";
    static String dbName = "";
    public ConnectionManager(JTextArea log, String ip, String username, String password, String dbName) {
        ConnectionManager.log = log;
        ConnectionManager.ip = ip;
        ConnectionManager.username = username;
        ConnectionManager.password = password;
        ConnectionManager.dbName = dbName;
    }
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            log.append("\nLoaded jdbc driver!");
            String connectionUrl = "jdbc:mysql://"+ip+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8&user="+username+"&password="+password;
            conn = DriverManager.getConnection(connectionUrl);
            log.append("\nConnected to mysql server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.append("\nCan't find jdbc driver!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.append("\nFailed to connect to mysql server!");

        }
        return conn;
    }

}
