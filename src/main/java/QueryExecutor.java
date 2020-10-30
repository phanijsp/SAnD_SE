

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {
    Connection connection;
    public QueryExecutor(Connection connection){
        this.connection = connection;
    }

    public long getTableLastUpdated(String query) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select ? from ? where ? = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, "lastUpdate");
            preparedStatement.setString(2, "updateLogs");
            preparedStatement.setString(3, "tableName");
            preparedStatement.setString(4, query);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(resultSet!=null){
            try {
                resultSet.absolute(0);
                return Long.parseLong(resultSet.getString(2));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return 0;
            }
        }else{
                return 0;
        }
    }

}
