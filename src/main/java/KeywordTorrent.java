public class KeywordTorrent {
    private String torrentTitle;
    private String torrentSeeds;
    private String torrentLeeches;
    private String torrentSize;
    private String torrentAdded;
    private String TorrentSource;
    private String TorrentSourceURL;
    private String TorrentMagnetURI;

    public KeywordTorrent(String torrentTitle, String torrentSeeds, String torrentLeeches, String torrentSize, String torrentAdded, String torrentSource, String torrentSourceURL, String torrentMagnetURI) {
        this.torrentTitle = torrentTitle;
        this.torrentSeeds = torrentSeeds;
        this.torrentLeeches = torrentLeeches;
        this.torrentSize = torrentSize;
        this.torrentAdded = torrentAdded;
        TorrentSource = torrentSource;
        TorrentSourceURL = torrentSourceURL;
        TorrentMagnetURI = torrentMagnetURI;
    }

    public String getTorrentTitle() {
        return torrentTitle;
    }

    public String getTorrentSeeds() {
        return torrentSeeds;
    }

    public String getTorrentLeeches() {
        return torrentLeeches;
    }

    public String getTorrentSize() {
        return torrentSize;
    }

    public String getTorrentAdded() {
        return torrentAdded;
    }

    public String getTorrentSource() {
        return TorrentSource;
    }

    public String getTorrentSourceURL() {
        return TorrentSourceURL;
    }

    public String getTorrentMagnetURI() {
        return TorrentMagnetURI;
    }
}
