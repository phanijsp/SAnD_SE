import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class TorrentSweeper {
    private ArrayList<TorrentDescriptor> torrent_search_results = new ArrayList<>();
    public ArrayList<TorrentDescriptor> getTorrents(String query) {
        torrent_search_results.clear();
        try {
            File file = new File("/home/phani_jsp/SAnD_SE/Engine.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String result = sb.toString();

            JSONObject jsonObject = new JSONObject(result);
            JSONArray field_descriptors_array = jsonObject.getJSONArray("descriptors");


            ArrayList<TorrentListGrabber> torrentListGrabbers = new ArrayList<>();
            for(int i = 0 ; i < field_descriptors_array.length() ; i ++){
                JSONObject descriptorObject = field_descriptors_array.getJSONObject(i);
                if(descriptorObject.keySet().size() == 11){
                    TorrentSource torrentSource = new TorrentSource(descriptorObject.getString("source"),
                            descriptorObject.getString("title_descriptor"),
                            descriptorObject.getString("seeds_descriptor"),
                            descriptorObject.getString("leeches_descriptor"),
                            descriptorObject.getString("size_descriptor"),
                            descriptorObject.getString("added_descriptor"),
                            descriptorObject.getString("endURLMagnet_descriptor"),
                            descriptorObject.getString("endURL_descriptor"),
                            descriptorObject.getString("searchURL"),
                            descriptorObject.getString("baseURL"),
                            descriptorObject.getString("queryIdentifier"));
                    TorrentListGrabber torrentListGrabber = new TorrentListGrabber(query, torrentSource);
                    torrentListGrabber.start();
                    torrentListGrabbers.add(torrentListGrabber);
                }

            }
                    while (true){
                        boolean done = true;
                        for (TorrentListGrabber torrentListGrabber : torrentListGrabbers) {
                            if (!torrentListGrabber.isJobDone()) {
                                done = false;
                                break;
                            }
                        }
                        if (!done) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            onJobDone(torrentListGrabbers);
                            break;
                        }
                    }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            onJobDone(new ArrayList<TorrentListGrabber>());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return torrent_search_results;
    }


    public void onJobDone(ArrayList<TorrentListGrabber> torrentListGrabbers) {
        for (TorrentListGrabber torrentListGrabber : torrentListGrabbers) {
            torrent_search_results.addAll(torrentListGrabber.getData());
        }
        torrentListGrabbers.clear();
    }
}
