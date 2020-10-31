

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
            PreparedStatement preparedStatement = connection.prepareStatement("Select lastUpdate from updateLogs where tableName = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, query);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(resultSet!=null){
            try {
                resultSet.last();
                if(resultSet.getRow()!=0){
                    resultSet.absolute(1);
                    return Long.parseLong(resultSet.getString(1));
                }else{
                    return 0;
                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return 0;
            }
        }else{
                return 0;
        }
    }

}
