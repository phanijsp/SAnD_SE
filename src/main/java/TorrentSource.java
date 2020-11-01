
public class TorrentSource {

    private String source;                  //[0]
    private String title_descriptor;        //[1]
    private String seeds_descriptor;        //[2]
    private String leeches_descriptor;      //[3]
    private String size_descriptor;         //[4]
    private String added_descriptor;        //[5]
    private String endURLMagnet_descriptor; //[6]
    private String endURL_descriptor;       //[7]
    private String searchURL;               //[8]
    private String baseURL;                 //[9]
    private String queryIdentifier;         //[10]



    public TorrentSource(String source,
                         String title_descriptor,
                         String seeds_descriptor,
                         String leeches_descriptor,
                         String size_descriptor,
                         String added_descriptor,
                         String endURLMagnet_descriptor,
                         String endURL_descriptor,
                         String searchURL,
                         String baseURL,
                         String queryIdentifier) {
        this.source = source;
        this.title_descriptor = title_descriptor;
        this.seeds_descriptor = seeds_descriptor;
        this.leeches_descriptor = leeches_descriptor;
        this.size_descriptor = size_descriptor;
        this.added_descriptor = added_descriptor;
        this.endURLMagnet_descriptor = endURLMagnet_descriptor;
        this.endURL_descriptor = endURL_descriptor;
        this.searchURL = searchURL;
        this.baseURL = baseURL;
        this.queryIdentifier = queryIdentifier;
    }

    public String getTitle_descriptor() {
        return title_descriptor;
    }

    public String getSeeds_descriptor() {
        return seeds_descriptor;
    }

    public String getLeeches_descriptor() {
        return leeches_descriptor;
    }

    public String getSize_descriptor() {
        return size_descriptor;
    }

    public String getAdded_descriptor() {
        return added_descriptor;
    }

    public String getEndURLMagnet_descriptor() {
        return endURLMagnet_descriptor;
    }

    public String getEndURL_descriptor() {
        return endURL_descriptor;
    }

    public String getSource() {
        return source;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public String getQueryIdentifier() {
        return queryIdentifier;
    }

    public String getBaseURL() {
        return baseURL;
    }

}
