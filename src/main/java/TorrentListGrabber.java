import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class TorrentListGrabber extends Thread {
    private TorrentSource torrentSource;
    private String searchURL;
    private ArrayList<TorrentDescriptor> torrentDescriptors = new ArrayList<>();
    private boolean jobDone = false;
    private int maxMagnetsPerSite = 6;
    String TAG = "here";

    public TorrentListGrabber(String query, TorrentSource torrentSource) throws UnsupportedEncodingException {
        this.torrentSource = torrentSource;
        this.searchURL = torrentSource.getSearchURL().replace(torrentSource.getQueryIdentifier(), URLEncoder.encode(query, "UTF-8"));
    }


    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(searchURL).followRedirects(true).timeout(5000).get();
            Elements endURLElements = doc.select(torrentSource.getEndURL_descriptor());
            Elements torrentTitleElements = doc.select(torrentSource.getTitle_descriptor());
            Elements seedElements = doc.select(torrentSource.getSeeds_descriptor());
            Elements leechElements = doc.select(torrentSource.getLeeches_descriptor());
            Elements sizeElements = doc.select(torrentSource.getSize_descriptor());
            Elements addedElements = doc.select(torrentSource.getAdded_descriptor());


            System.out.println("Found eUE " + endURLElements.size() +
                    "tTE " + torrentTitleElements.size() +
                    "sE " + seedElements.size() +
                    "lE " + leechElements.size() +
                    "sizeE " + sizeElements.size() +
                    "aE" + addedElements.size() + " at \n" + searchURL);

            if (isValidData(new Elements[]{endURLElements, torrentTitleElements, seedElements, leechElements, sizeElements, addedElements})) {
                ArrayList<Thread> torrentDescriptorThreads = new ArrayList<>();
                if(maxMagnetsPerSite > endURLElements.size()){
                    maxMagnetsPerSite = endURLElements.size();
                }
                for (int i = 0; i < maxMagnetsPerSite; i++) {
                    int finalI = i;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            String magnetLink = getMagnet(endURLElements.get(finalI).attr("href"));
                            if (magnetLink.startsWith("magnet")) {
                                torrentDescriptors.add(
                                        new TorrentDescriptor(
                                                torrentSource.getBaseURL(),
                                                endURLElements.get(finalI).attr("href"),
                                                torrentTitleElements.get(finalI).text(),
                                                seedElements.get(finalI).text(),
                                                leechElements.get(finalI).text(),
                                                sizeElements.get(finalI).text(),
                                                addedElements.get(finalI).text(),
                                                torrentSource.getSource(),
                                                magnetLink
                                        ));
                            }
                        }
                    };
                    t.start();
                    torrentDescriptorThreads.add(t);
                }
                while (true) {
                    boolean done = true;
                    for (Thread t : torrentDescriptorThreads) {
                        if (t.isAlive()) {
                            done = false;
                            break;
                        }
                    }
                    if (done) {
                        break;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Exception at 89 of TorrentListGrabber while trying to parse url "+searchURL);
            e.printStackTrace();
        }
        jobDone = true;
    }


    private boolean isValidData(Elements[] elements_array) {
        boolean valid = true;
        for (Elements elements : elements_array) {
            if (elements_array[0].size() != elements.size()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public boolean isJobDone() {
        return jobDone;
    }

    public ArrayList<TorrentDescriptor> getData() {
        return torrentDescriptors;
    }

    private String getMagnet(String endUrl) {
        String magnetLink = "";
        try {
            Document document = Jsoup.connect(new Utils().appendBaseEndUrls(torrentSource.getBaseURL(), endUrl)).followRedirects(true).timeout(5000).get(); //torrentSource.getBaseURL() + endUrl
            Elements magnets = document.select(torrentSource.getEndURLMagnet_descriptor());
            if (magnets.size() > 0) {
                return magnets.get(0).attr("href");
            }
        } catch (IOException e) {
            System.out.println("IOException at line 124 of TorrentListGrabber.java while trying to parse magnet link from url "+endUrl);
        }
        return magnetLink;
    }
}
