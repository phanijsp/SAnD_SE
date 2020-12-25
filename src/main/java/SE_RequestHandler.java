
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;


public class SE_RequestHandler extends Thread {
    Socket socket;
    private final String searchConstant = "·½ÄÒÕØÞðøúþĂĔĜĦ";

    public SE_RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\nClient at " + socket.getInetAddress().toString());
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            String searchQuery = dataInputStream.readUTF();
            if (searchQuery.startsWith(searchConstant)) {
                searchQuery = searchQuery.replace(searchConstant, "");
                searchQuery = searchQuery.trim().toLowerCase();
                System.out.println("\nClient at " + socket.getInetAddress().toString() + " searched for " + searchQuery);

                String result = getTorrentsInJSON(searchQuery);
                objectOutputStream.writeObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getTorrentsInJSON(String searchQuery) {
        ArrayList<TorrentDescriptor> torrentDescriptors = new TorrentSweeper().getTorrents(searchQuery);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < torrentDescriptors.size(); i++) {
            JSONObject jsonObjectKT = new JSONObject();
            TorrentDescriptor torrentDescriptor = torrentDescriptors.get(i);
            jsonObjectKT.put("torrentTitle", torrentDescriptor.getTitle());
            jsonObjectKT.put("torrentSeeds", torrentDescriptor.getSeeds());
            jsonObjectKT.put("torrentLeeches", torrentDescriptor.getLeeches());
            jsonObjectKT.put("torrentSize", torrentDescriptor.getSize());
            jsonObjectKT.put("torrentAdded", torrentDescriptor.getAdded());
            jsonObjectKT.put("TorrentSource", torrentDescriptor.getSource());
            jsonObjectKT.put("TorrentSourceURL", torrentDescriptor.getBaseURL() + torrentDescriptor.getEndURL());
            jsonObjectKT.put("TorrentMagnetURI", torrentDescriptor.getEndURLFieldsDescriptor());
            jsonArray.put(i, jsonObjectKT);
        }
        jsonObject.put("keyword_torrents", jsonArray);
        return jsonObject.toString();
    }

}
