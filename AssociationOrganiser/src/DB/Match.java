package DB;

import java.util.ArrayList;

public class Match {

    // TODO: add instance and class variables + methods for Match class

    private String homeTeamName;
    private String awayTeamName;
    private String winningTeamName;
    private String homePlayer1Name;
    private String homePlayer2Name;
    private String awayPlayer1Name;
    private String awayPlayer2Name;
    private ArrayList<Set> sets;
    private boolean complete;
    private int homeTeamSetsWon;
    private int awayTeamSetsWon;

    public Match(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.complete = false;
    }

    public Match(String homeTeamName, String awayTeamName, String homePlayer1Name, String homePlayer2Name, String awayPlayer1Name, String awayPlayer2Name, String winningTeamName, int homeTeamSetsWon, int awayTeamSetsWon) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homePlayer1Name = homePlayer1Name;
        this.homePlayer2Name = homePlayer2Name;
        this.awayPlayer1Name = awayPlayer1Name;
        this.awayPlayer2Name = awayPlayer2Name;
        this.winningTeamName = winningTeamName;
        this.homeTeamSetsWon = homeTeamSetsWon;
        this.awayTeamSetsWon = awayTeamSetsWon;
        this.complete = true;
        // TODO: load in sets here
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) { this.homeTeamName = homeTeamName; }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getWinningTeamName() {
        return winningTeamName;
    }

    public void setWinningTeamName(String winningTeamName) {
        this.winningTeamName = winningTeamName;
    }

    public String getHomePlayer1Name() {
        return homePlayer1Name;
    }

    public void setHomePlayer1Name(String homePlayer1Name) {
        this.homePlayer1Name = homePlayer1Name;
    }

    public String getHomePlayer2Name() {
        return homePlayer2Name;
    }

    public void setHomePlayer2Name(String homePlayer2Name) {
        this.homePlayer2Name = homePlayer2Name;
    }

    public String getAwayPlayer1Name() {
        return awayPlayer1Name;
    }

    public void setAwayPlayer1Name(String awayPlayer1Name) {
        this.awayPlayer1Name = awayPlayer1Name;
    }

    public String getAwayPlayer2Name() {
        return awayPlayer2Name;
    }

    public void setAwayPlayer2Name(String awayPlayer2Name) {
        this.awayPlayer2Name = awayPlayer2Name;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) { this.complete = complete; }

    public int getHomeTeamSetsWon() {
        return homeTeamSetsWon;
    }

    public void setHomeTeamSetsWon(int homeTeamSetsWon) { this.homeTeamSetsWon = homeTeamSetsWon; }

    public int getAwayTeamSetsWon() { return awayTeamSetsWon; }

    public void setAwayTeamSetsWon(int awayTeamSetsWon) { this.awayTeamSetsWon = awayTeamSetsWon; }
}

    /*private void retrieveMatchPlayers() {

        ArrayList<String[]> data = new ArrayList<>();

        String getHID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + ")";
        String getAID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + ")";
        String getMatchID = "SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHID +") AND AwayTeamID = ("+ getAID + "));";

        String select = "SELECT \n" +
                "HP1.ID AS HP1N, \n" +
                "HP2.ID AS HP2N, \n" +
                "AP1.ID AS AP1N, \n" +
                "AP2.ID AS AP2N \n" +
                "FROM `Match` AS m \n" +
                "LEFT JOIN Player AS HP1 ON m.HomePlayer1ID = HP1.ID \n" +
                "LEFT JOIN Player AS HP2 ON m.HomePlayer2ID = HP2.ID \n" +
                "LEFT JOIN Player AS AP1 ON m.AwayPlayer1ID = AP1.ID \n" +
                "LEFT JOIN Player AS AP2 ON m.AwayPlayer2ID = AP2.ID " +
                "WHERE (m.ID = "+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +");";

        data = DatabaseManager.executeQuery(select);

        // TODO: make these turn into names :)
        this.homePlayer1 = data.get(0)[0];
        this.homePlayer2 = data.get(0)[1];
        this.awayPlayer1 = data.get(0)[2];
        this.awayPlayer2 = data.get(0)[3];
    }

    private String generateSendPlayerQuery(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";

        return "UPDATE `Match` SET HomePlayer1ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";
    }

    void sendHomePlayer1ToDatabase(String playerName) {

        DatabaseManager.insertData(generateSendPlayerQuery(playerName));
    }

    void sendHomePlayer2ToDatabase(String playerName) {

        DatabaseManager.insertData(generateSendPlayerQuery(playerName));
    }

    void sendAwayPlayer1ToDatabase(String playerName) {

        DatabaseManager.insertData(generateSendPlayerQuery(playerName));
    }

    void sendAwayPlayer2ToDatabase(String playerName) {

        DatabaseManager.insertData(generateSendPlayerQuery(playerName));
    }*/

    /**********************************************STATIC METHODS******************************************************/

    /*static void generateMatches() {

        for (Team team_outer : Team.getTeamList()) {

            // iterate over every team, once per team - ignore when passing over self
            for (Team team_inner : Team.getTeamList()) {

                // create a match every time
                if (team_inner.getTeamID() != team_outer.getTeamID()) {

                    Match.createMatch(team_inner.getTeamName(), team_outer.getTeamName());
                }
            }
        }
    }*/

    /*private static void createMatch(String HomeTeamName, String AwayTeamName) {

        // send to database: HomeTeamID & AwayTeamID (using their names i suppose)
        // Players select after game is played? I dont actually know
        // Winner is added after game is played (naturally)
        String insert = "INSERT INTO `Match` (HomeTeamID, AwayTeamID) VALUES (";

        String getHTID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(HomeTeamName) + ")";
        String getATID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(AwayTeamName) + ")";

        insert += getHTID + "," + getATID + ");";

        DatabaseManager.insertData(insert);
    }*/
