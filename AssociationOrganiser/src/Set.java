import java.util.ArrayList;

// TODO: add Set class
public class Set {

    private int HomePlayer1ID;
    private String HomePlayer1Name;
    private int HomePlayer2ID;
    private String HomePlayer2Name;
    private int AwayPlayer1ID;
    private String AwayPlayer1Name;
    private int AwayPlayer2ID;
    private String AwayPlayer2Name;
    private int HomeScore;
    private int AwayScore;
    private ArrayList<Game> gameList;

    public Set(int homePlayer1ID, String homePlayer1Name, int homePlayer2ID, String homePlayer2Name, int awayPlayer1ID, String awayPlayer1Name, int awayPlayer2ID, String awayPlayer2Name) {
        HomePlayer1ID = homePlayer1ID;
        HomePlayer1Name = homePlayer1Name;
        HomePlayer2ID = homePlayer2ID;
        HomePlayer2Name = homePlayer2Name;
        AwayPlayer1ID = awayPlayer1ID;
        AwayPlayer1Name = awayPlayer1Name;
        AwayPlayer2ID = awayPlayer2ID;
        AwayPlayer2Name = awayPlayer2Name;
        this.gameList = new ArrayList<>();
    }

    static void generateSetsForMatch(String HomeTeamName, String AwayTeamName) {


    }

    static void generateSet(String HomeTeamName, String AwayTeamName) {

        //TODO: replace HomeTeamName and AwayTeamName with references to TeamID's
        String insert = "INSERT INTO `Set` (MatchID) VALUES (";
        insert += "(SELECT ID FROM `Match` WHERE (" +
                "HomeTeamName = \"UWE\"" +
                " AND " +
                "AwayTeamName = \"Page\")));";

        DatabaseManager.insertData(insert);
    }

    public int getHomePlayer1ID() {
        return HomePlayer1ID;
    }

    public void setHomePlayer1ID(int homePlayer1ID) {
        HomePlayer1ID = homePlayer1ID;
    }

    public String getHomePlayer1Name() {
        return HomePlayer1Name;
    }

    public void setHomePlayer1Name(String homePlayer1Name) {
        HomePlayer1Name = homePlayer1Name;
    }

    public int getHomePlayer2ID() {
        return HomePlayer2ID;
    }

    public void setHomePlayer2ID(int homePlayer2ID) {
        HomePlayer2ID = homePlayer2ID;
    }

    public String getHomePlayer2Name() {
        return HomePlayer2Name;
    }

    public void setHomePlayer2Name(String homePlayer2Name) {
        HomePlayer2Name = homePlayer2Name;
    }

    public int getAwayPlayer1ID() {
        return AwayPlayer1ID;
    }

    public void setAwayPlayer1ID(int awayPlayer1ID) {
        AwayPlayer1ID = awayPlayer1ID;
    }

    public String getAwayPlayer1Name() {
        return AwayPlayer1Name;
    }

    public void setAwayPlayer1Name(String awayPlayer1Name) {
        AwayPlayer1Name = awayPlayer1Name;
    }

    public int getAwayPlayer2ID() {
        return AwayPlayer2ID;
    }

    public void setAwayPlayer2ID(int awayPlayer2ID) {
        AwayPlayer2ID = awayPlayer2ID;
    }

    public String getAwayPlayer2Name() {
        return AwayPlayer2Name;
    }

    public void setAwayPlayer2Name(String awayPlayer2Name) {
        AwayPlayer2Name = awayPlayer2Name;
    }

    public int getHomeScore() {
        return HomeScore;
    }

    public void setHomeScore(int homeScore) {
        HomeScore = homeScore;
    }

    public int getAwayScore() {
        return AwayScore;
    }

    public void setAwayScore(int awayScore) {
        AwayScore = awayScore;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }
}
