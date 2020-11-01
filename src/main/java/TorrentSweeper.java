import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TorrentSweeper {
    private ArrayList<TorrentDescriptor> torrent_search_results = new ArrayList<>();
    public ArrayList<TorrentDescriptor> getTorrents(String query, JSONArray field_descriptors_array) {
        torrent_search_results.clear();
        try {
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
                        System.out.println(torrentListGrabbers.size());
                        for (TorrentListGrabber torrentListGrabber : torrentListGrabbers) {
                            if (!torrentListGrabber.isJobDone()) {
                                done = false;
                                break;
                            }
                        }
                        if (!done) {
                            System.out.println("run: not done");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println( "run: all job done");
                            onJobDone(torrentListGrabbers);
                            break;
                        }
                    }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            onJobDone(new ArrayList<TorrentListGrabber>());
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
