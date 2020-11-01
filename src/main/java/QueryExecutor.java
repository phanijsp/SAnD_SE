

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public void updateTableLogs(String tableName, long lastUpdated){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `updateLogs` VALUES(?, ?) ON DUPLICATE KEY UPDATE `lastUpdate` = ?");
            preparedStatement.setString(1, tableName);
            preparedStatement.setLong(2, lastUpdated);
            preparedStatement.setLong(3, lastUpdated);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void dropTableIfExists(String tableName){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`");
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void createTorrentDataTable(String tableName){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE `"+tableName+"`(torrentTitle VARCHAR(512)," +
                    " torrentSeeds INT," +
                    " torrentLeeches INT," +
                    " torrentSize TEXT NOT NULL," +
                    " torrentAdded TEXT NOT NULL," +
                    " TorrentSource TEXT NOT NULL," +
                    " TorrentSourceURL TEXT NOT NULL," +
                    " TorrentMagnetURI TEXT NOT NULL)");
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void insertTorrentDataIntoTable(String tableName, ArrayList<TorrentDescriptor> torrentDescriptors){
        for(TorrentDescriptor torrentDescriptor : torrentDescriptors){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `"+tableName+"` VALUES(?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, torrentDescriptor.getTitle());
                preparedStatement.setString(2, torrentDescriptor.getSeeds());
                preparedStatement.setString(3, torrentDescriptor.getLeeches());
                preparedStatement.setString(4, torrentDescriptor.getSize());
                preparedStatement.setString(5, torrentDescriptor.getAdded());
                preparedStatement.setString(6, torrentDescriptor.getSource());
                preparedStatement.setString(7, (torrentDescriptor.getBaseURL()+torrentDescriptor.getEndURL()));
                preparedStatement.setString(8, torrentDescriptor.getEndURLFieldsDescriptor());
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

}
