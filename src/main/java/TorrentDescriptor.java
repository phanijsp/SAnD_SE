public class TorrentDescriptor {


    public String getEndURL() {
        return endURL;
    }


    public String getTitle() {
        return title;
    }

    public String getSeeds() {
        return seeds;
    }

    public String getLeeches() {
        return leeches;
    }

    public String getSize() {
        return size;
    }

    public String getAdded() {
        return added;
    }

    private String baseURL;
    private String endURL;

    public TorrentDescriptor(String baseURL, String endURL, String title, String seeds, String leeches, String size, String added, String endURLMagnetFieldDescriptor) {
        this.baseURL = baseURL;
        this.endURL = endURL;
        this.title = title;
        this.seeds = seeds;
        this.leeches = leeches;
        this.size = size;
        this.added = added;
        this.endURLMagnetFieldsDescriptor = endURLMagnetFieldDescriptor;

    }

    public String getEndURLFieldsDescriptor() {
        return endURLMagnetFieldsDescriptor;
    }
    private String endURLMagnetFieldsDescriptor;
    private String title;
    private String seeds;
    private String leeches;
    private String size;
    private String added;

    public String getBaseURL() {
        return baseURL;
    }


}

