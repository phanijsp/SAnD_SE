
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;


public class SE_RequestHandler{
    Socket socket;

    public SE_RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            String searchQuery = dataInputStream.readUTF();
            String searchConstant = "·½ÄÒÕØÞðøúþĂĔĜĦ";
            if (searchQuery.startsWith(searchConstant)) {
                searchQuery = searchQuery.replace(searchConstant, "");
                searchQuery = searchQuery.trim().toLowerCase();
                System.out.println("\n\n\nRequest from " + socket.getInetAddress().toString() + " searched for " + searchQuery);

                String result = getTorrentsInJSON(searchQuery);
                objectOutputStream.writeObject(result);
            }
        } catch (IOException e) {
            System.out.println("IOException occurred at line 36 of SE_RequestHandler.java");
        }
        if (socket != null) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("IOException occurred at line 36 of SE_RequestHandler.java while trying to close a socket");
                }
            }
        }
        System.gc();
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
            jsonObjectKT.put("TorrentSourceURL",  new Utils().appendBaseEndUrls(torrentDescriptor.getBaseURL(), torrentDescriptor.getEndURL())); //torrentDescriptor.getBaseURL() + torrentDescriptor.getEndURL())
            jsonObjectKT.put("TorrentMagnetURI", torrentDescriptor.getEndURLFieldsDescriptor());
            jsonArray.put(i, jsonObjectKT);
        }
        jsonObject.put("keyword_torrents", jsonArray);
        return jsonObject.toString();
    }

}
