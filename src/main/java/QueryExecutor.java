
import java.sql.Connection;
import java.sql.ResultSet;

public class QueryExecutor {
    Connection connection;
    public QueryExecutor(Connection connection){
        this.connection = connection;
    }

    public ResultSet executeQuery(String query) {
        return null;
    }

}
