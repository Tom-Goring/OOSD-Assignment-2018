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

    static void generateMatches() {

        for (Team team_outer : Team.getTeamList()) {

            // iterate over every team, once per team - ignore when passing over self
            for (Team team_inner : Team.getTeamList()) {

                // create a match every time
                if (team_inner.getTeamID() != team_outer.getTeamID()) {

                    Match.createMatch(team_inner.getTeamName(), team_outer.getTeamName());
                }
            }
        }
    }

    private static void createMatch(String HomeTeamName, String AwayTeamName) {

        // send to database: HomeTeamID & AwayTeamID (using their names i suppose)
        // Players select after game is played? I dont actually know
        // Winner is added after game is played (naturally)
        String insert = "INSERT INTO `Match` (HomeTeamID, AwayTeamID) VALUES (";

        String getHTID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(HomeTeamName) + ")";
        String getATID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(AwayTeamName) + ")";

        insert += getHTID + "," + getATID + ");";

        DatabaseManager.insertData(insert);
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
