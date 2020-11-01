
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class SE_RequestHandler extends Thread {
    Socket socket;
    private final long timeConstant = 3600000;
    public SE_RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\nClient at " + socket.getInetAddress().toString());
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String searchQuery = dataInputStream.readUTF();
            System.out.println("\nClient at " + socket.getInetAddress().toString() + " searched for " + searchQuery);
            QueryExecutor queryExecutor = new QueryExecutor(ConnectionManager.getConnection());

            long lastUpdateTime = queryExecutor.getTableLastUpdated(searchQuery);

            if((lastUpdateTime == 0) ||
                    ((System.currentTimeMillis() - lastUpdateTime) > timeConstant)){
                updateKeywordTable(searchQuery);
            }else{
                getKeywordTable(searchQuery);
            }

            dataOutputStream.writeUTF("{\"name\":\"blablabla\"}");
            dataOutputStream.writeUTF("!Q2!89!@09!@");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void updateKeywordTable(String searchQuery){
        TorrentSweeper torrentSweeper = new TorrentSweeper();
        ArrayList<TorrentDescriptor> torrentDescriptors = torrentSweeper.getTorrents(searchQuery);
        System.out.println("In updateKeywordTable:" + torrentDescriptors.size());
    }
    public void getKeywordTable(String searchQuery){

    }

}
