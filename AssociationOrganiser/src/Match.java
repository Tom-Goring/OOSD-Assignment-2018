import java.util.ArrayList;

// TODO: add Match class
public class Match {

    private int HomeTeamID;
    private String HomeTeamName;

    private int AwayTeamID;
    private String AwayTeamName;

    private ArrayList<Set> setList;

    private String finalScore;

    private int WinnerID;
    private int WinnerName;

    Match(int HomeTeamID, String HomeTeamName, int AwayTeamID, String AwayTeamName) {

        this.HomeTeamID = HomeTeamID;
        this.HomeTeamName = HomeTeamName;
        this.AwayTeamID = AwayTeamID;
        this.AwayTeamName = AwayTeamName;
    }

    static void createMatch(int HTID, int ATID) {

        // send to database: HomeTeamID & AwayTeamID (using their names i suppose)
        // Players select after game is played? I dont actually know
        // Winner is added after game is played (naturally)
        String insert = "INSERT INTO `Match` (HomeTeamID, AwayTeamID) VALUES (";

        insert += HTID + ", " + ATID  + ")";

        DatabaseManager.insertData(insert);

        // createMatchSets
    }

    public int getHomeTeamID() {
        return HomeTeamID;
    }

    public void setHomeTeamID(int homeTeamID) {
        HomeTeamID = homeTeamID;
    }

    public String getHomeTeamName() {
        return HomeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        HomeTeamName = homeTeamName;
    }

    public int getAwayTeamID() {
        return AwayTeamID;
    }

    public void setAwayTeamID(int awayTeamID) {
        AwayTeamID = awayTeamID;
    }

    public String getAwayTeamName() {
        return AwayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        AwayTeamName = awayTeamName;
    }

    public ArrayList<Set> getSetList() {
        return setList;
    }

    public void setSetList(ArrayList<Set> setList) {
        this.setList = setList;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public int getWinnerID() {
        return WinnerID;
    }

    public void setWinnerID(int winnerID) {
        WinnerID = winnerID;
    }

    public int getWinnerName() {
        return WinnerName;
    }

    public void setWinnerName(int winnerName) {
        WinnerName = winnerName;
    }
}
