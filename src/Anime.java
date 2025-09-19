public class Anime implements Comparable<Anime> {
    private String title;
    private Integer rank;
    private String type;
    private int episodes;
    private String aired;
    private int members;
    private String pageUrl;
    private String imageUrl;
    private double score;
    private String unknownEpisodes;

    //Parameterized constructor for Anime object
    public Anime(String title, int rank, String type, int episodes, String aired, int members, String pageUrl, String imageUrl, double score) {
        this.title = title;
        this.rank = rank;
        this.type = type;
        this.episodes = episodes;
        this.unknownEpisodes = "";
        this.aired = aired;
        this.members = members;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.score = score;
    }

    //constructor for when there is an unknown number of episodes - so the episodes are a string rather than an int
    public Anime(String title, int rank, String type, String episodes, String aired, int members, String pageUrl, String imageUrl, double score) {
        this.title = title;
        this.rank = rank;
        this.type = type;
        this.episodes = 0;
        unknownEpisodes = episodes;
        this.aired = aired;
        this.members = members;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.score = score;
    }

    //default constructor
    public Anime() {
        title = "?";
        rank = 0;
        type = "?";
        episodes = 0;
        unknownEpisodes = "?";
        aired = "?";
        members = 0;
        pageUrl = "?";
        imageUrl = "?";
        score = 0;
    }

    //Copy Constructor //FIXME check that this is how to do it
    public Anime(Anime anime) {
        title = anime.getTitle();
        rank = anime.getRank();
        type = anime.getType();
        episodes = anime.getEpisodes();
        unknownEpisodes = anime.getUnknownEpisodes();
        aired = anime.getAired();
        members = anime.getMembers();
        pageUrl = anime.getPageUrl();
        imageUrl = anime.getImageUrl();
        score = anime.getScore();
    }

    //getter methods
    public String getTitle() { return title; }
    public Integer getRank() { return rank; }
    public String getType() { return type; }
    public int getEpisodes() { return episodes; }
    public String getAired() { return aired; }
    public int getMembers() { return members; }
    public String getPageUrl() { return pageUrl; }
    public String getImageUrl() { return imageUrl; }
    public double getScore() { return score; }
    public String getUnknownEpisodes() { return unknownEpisodes; }

    @Override
    public String toString() {
        return "rank: " + rank + ", Title: " + title + ", type: " + type + ", Number of episodes: " + episodes + ", Date Aired: " + aired + ", members: " + members + ", page Url: " + pageUrl + ", image Url: " + imageUrl + ", score: " + score;
    }
    public String toString(String unknownEpisodes) {
        return "rank: " + rank + "Title: " + title + ", type: " + type + ", Number of episodes: ?, Date Aired: " + aired + ", members: " + members + ", page Url: " + pageUrl + ", image Url: " + imageUrl + ", score: " + score;
    }


    //FIXME
    //This method returns true if every part of the anime object is exactly the same.
    //For this data set, technically no two objects should be the same.
    public boolean equals(Anime obj) {
        boolean isEqual = false;
        if (obj.getTitle().equals(title) && obj.getRank().equals(rank) && obj.getType().equals(type) && obj.getAired().equals(aired) && obj.getMembers() == members && obj.getPageUrl().equals(pageUrl) && obj.getImageUrl().equals(imageUrl) && (Math.abs(obj.getScore() - score) < 0.00001)) {
            //some anime objects have unkonwn episodes of string value with a ? so this check compares between the two data types: int and String
            if (episodes == 0 && obj.getEpisodes() == 0) {
                isEqual = unknownEpisodes.equals(obj.getUnknownEpisodes());
            }
            else if (episodes != 0 && obj.getEpisodes() != 0) {
                isEqual = episodes == obj.getEpisodes();
            }
        }
        return isEqual;
    }

    @Override
    public int compareTo(Anime anime2) {
        return this.rank.compareTo(anime2.getRank());
    }

}
