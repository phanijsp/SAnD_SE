
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class SE_RequestHandler extends Thread {
    Socket socket;
    private final long timeConstant = 3600000;
    QueryExecutor queryExecutor;
    public SE_RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\nClient at " + socket.getInetAddress().toString());
        queryExecutor = new QueryExecutor(ConnectionManager.getConnection());
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String searchQuery = dataInputStream.readUTF();
            System.out.println("\nClient at " + socket.getInetAddress().toString() + " searched for " + searchQuery);


            long lastUpdateTime = queryExecutor.getTableLastUpdated(searchQuery);

            if((lastUpdateTime == 0) ||
                    ((System.currentTimeMillis() - lastUpdateTime) > timeConstant)){
                updateKeywordTable(searchQuery);
            }
            String result = queryExecutor.getKeywordTorrentsInJSON(searchQuery).toString();

            dataOutputStream.writeUTF(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void updateKeywordTable(String searchQuery){
        TorrentSweeper torrentSweeper = new TorrentSweeper();
        ArrayList<TorrentDescriptor> torrentDescriptors = torrentSweeper.getTorrents(searchQuery);
        System.out.println("In updateKeywordTable:" + torrentDescriptors.size());
        if(torrentDescriptors.size()>0){
            System.out.println("Updating table "+searchQuery);
            queryExecutor.dropTableIfExists(searchQuery);
            queryExecutor.createTorrentDataTable(searchQuery);
            queryExecutor.insertTorrentDataIntoTable(searchQuery, torrentDescriptors);
            queryExecutor.updateTableLogs(searchQuery, System.currentTimeMillis());
        }
    }
}
