
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class SE_RequestHandler{
    Socket socket;
    private final long timeConstant = 36000000;
    private final String searchConstant = "·½ÄÒÕØÞðøúþĂĔĜĦ";
    private final String trendingConstant = "ĨĭĲĸļłŇŌŕś";
    QueryExecutor queryExecutor;

    public SE_RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("\nClient at " + socket.getInetAddress().toString());
        queryExecutor = new QueryExecutor(ConnectionManager.getConnection());
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Awaiting request from : "+socket.getInetAddress().getHostAddress());
            String searchQuery = dataInputStream.readUTF();
            System.out.println("Received request from : "+socket.getInetAddress().getHostAddress()+", "+searchQuery);

            if (searchQuery.startsWith(searchConstant)) {
                searchQuery = searchQuery.replace(searchConstant, "");
                searchQuery = searchQuery.trim().toLowerCase();
                System.out.println("\nClient at " + socket.getInetAddress().toString() + " searched for " + searchQuery);

                long lastUpdateTime = queryExecutor.getTableLastUpdated(searchQuery);

                if ((lastUpdateTime == 0) ||
                        ((System.currentTimeMillis() - lastUpdateTime) > timeConstant)) {
                    System.out.println("Updating table for " + searchQuery + " ...");
                    updateKeywordTable(searchQuery);
                }
                String result = queryExecutor.getKeywordTorrentsInJSON(searchQuery).toString();
                objectOutputStream.writeObject(result);
            }else if(searchQuery.startsWith(trendingConstant)){
                System.out.println("trending request "+searchQuery+" from "+socket.getInetAddress());
                searchQuery = searchQuery.replace(trendingConstant,"");
                if(searchQuery.equals("get")){
                    String result = queryExecutor.getTrending();
                    System.out.println("Received a get trending request from client at "+socket.getInetAddress());
                    objectOutputStream.writeObject(result);
                    System.out.println("Wrote response "+result+"to client at "+socket.getInetAddress());

                }else if(searchQuery.equals("set")){
                    System.out.println("Received a set trending request from client at "+socket.getInetAddress());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(socket!=null){
            if(!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateKeywordTable(String searchQuery) {
        TorrentSweeper torrentSweeper = new TorrentSweeper();
        ArrayList<TorrentDescriptor> torrentDescriptors = torrentSweeper.getTorrents(searchQuery);
        System.out.println("In updateKeywordTable:" + torrentDescriptors.size());
        if (torrentDescriptors.size() > 0) {
            System.out.println("Updating table " + searchQuery);
            queryExecutor.dropTableIfExists(searchQuery);
            queryExecutor.createTorrentDataTable(searchQuery);
            queryExecutor.insertTorrentDataIntoTable(searchQuery, torrentDescriptors);
            queryExecutor.updateTableLogs(searchQuery, System.currentTimeMillis());
        }
    }
}
