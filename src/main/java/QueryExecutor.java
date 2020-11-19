

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryExecutor {
    Connection connection;

    public QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public long getTableLastUpdated(String tableName) {
        tableName = "keyword"+tableName;

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("Select lastUpdate from updateLogs where tableName = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, tableName);

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                    resultSet.last();
                    if (resultSet.getRow() != 0) {
                        resultSet.absolute(1);
                        long value = Long.parseLong(resultSet.getString(1));
                        preparedStatement.close();
                        resultSet.close();

                        return value;
                    } else {
                        preparedStatement.close();
                        resultSet.close();
                        return 0;
                    }
            } else {
                return 0;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            try {
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(resultSet!=null){
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return 0;
        }

    }

    public void updateTableLogs(String tableName, long lastUpdated) {
        tableName = "keyword"+tableName;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO `updateLogs` VALUES(?, ?) ON DUPLICATE KEY UPDATE `lastUpdate` = ?");
            preparedStatement.setString(1, tableName);
            preparedStatement.setLong(2, lastUpdated);
            preparedStatement.setLong(3, lastUpdated);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            sqlException.printStackTrace();
        }
    }

    public void dropTableIfExists(String tableName) {
        tableName = "keyword"+tableName;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS `" + tableName + "`");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            sqlException.printStackTrace();
        }
    }

    public void createTorrentDataTable(String tableName) {
        tableName = "keyword"+tableName;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE `" + tableName + "`(torrentTitle VARCHAR(512)," +
                    " torrentSeeds INT," +
                    " torrentLeeches INT," +
                    " torrentSize TEXT NOT NULL," +
                    " torrentAdded TEXT NOT NULL," +
                    " TorrentSource TEXT NOT NULL," +
                    " TorrentSourceURL TEXT NOT NULL," +
                    " TorrentMagnetURI TEXT NOT NULL)");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            sqlException.printStackTrace();
        }

    }

    public void insertTorrentDataIntoTable(String tableName, ArrayList<TorrentDescriptor> torrentDescriptors) {
        tableName = "keyword"+tableName;
        PreparedStatement preparedStatement = null;
        for (TorrentDescriptor torrentDescriptor : torrentDescriptors) {
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO `" + tableName + "` VALUES(?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, torrentDescriptor.getTitle());
                preparedStatement.setString(2, torrentDescriptor.getSeeds().replace(",",""));
                preparedStatement.setString(3, torrentDescriptor.getLeeches().replace(",",""));
                preparedStatement.setString(4, torrentDescriptor.getSize());
                preparedStatement.setString(5, torrentDescriptor.getAdded());
                preparedStatement.setString(6, torrentDescriptor.getSource());
                preparedStatement.setString(7, (torrentDescriptor.getBaseURL() + torrentDescriptor.getEndURL()));
                preparedStatement.setString(8, torrentDescriptor.getEndURLFieldsDescriptor());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException sqlException) {
                if(preparedStatement!=null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                sqlException.printStackTrace();
                System.out.println("\ntitle : "+torrentDescriptor.getTitle()+"\n");
                System.out.println("\nSeeds : "+torrentDescriptor.getSeeds()+"\n");
                System.out.println("\nLeeches : "+torrentDescriptor.getSeeds()+"\n");
            }
        }
    }

    public ArrayList<KeywordTorrent> getTorrentDataFromKeywordTable(String tableName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<KeywordTorrent> keywordTorrents = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `" + tableName + "`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keywordTorrents.add(new KeywordTorrent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            try {
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(resultSet!=null){
                    resultSet.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sqlException.printStackTrace();
        }
        return keywordTorrents;
    }

    public JSONObject getKeywordTorrentsInJSON(String tableName) {
        tableName = "keyword"+tableName;

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<KeywordTorrent> keywordTorrents = getTorrentDataFromKeywordTable(tableName);
        for (int i = 0; i < keywordTorrents.size(); i++) {
            JSONObject jsonObjectKT = new JSONObject();
            KeywordTorrent keywordTorrent = keywordTorrents.get(i);
            jsonObjectKT.put("torrentTitle", keywordTorrent.getTorrentTitle());
            jsonObjectKT.put("torrentSeeds", keywordTorrent.getTorrentSeeds());
            jsonObjectKT.put("torrentLeeches", keywordTorrent.getTorrentLeeches());
            jsonObjectKT.put("torrentSize", keywordTorrent.getTorrentSize());
            jsonObjectKT.put("torrentAdded", keywordTorrent.getTorrentAdded());
            jsonObjectKT.put("TorrentSource", keywordTorrent.getTorrentSource());
            jsonObjectKT.put("TorrentSourceURL", keywordTorrent.getTorrentSourceURL());
            jsonObjectKT.put("TorrentMagnetURI", keywordTorrent.getTorrentMagnetURI());
            jsonArray.put(i, jsonObjectKT);
        }
        jsonObject.put("keyword_torrents", jsonArray);
        return jsonObject;
    }

    public String getTrending(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from `trending`");
            resultSet = preparedStatement.executeQuery();
            StringBuilder result = new StringBuilder();
            while(resultSet.next()){
                result.append("·").append(resultSet.getString("title"));
            }
            preparedStatement.close();
            resultSet.close();
            return result.toString();
        } catch (SQLException sqlException) {
            try {
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(resultSet!=null){
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sqlException.printStackTrace();
            return null;
        }
    }

}
