import java.util.ArrayList;

public class Match {

    // TODO: add instance and class variables + methods for Match class

    private String homeTeamName;
    private String awayTeamName;

    // TODO: consider making these actual player classes
    private String homePlayer1;
    private String homePlayer2;
    private String awayPlayer1;
    private String awayPlayer2;

    int homeTeamSetsWon;
    int awayTeamSetsWon;

    String winningTeam;

    public Match(String homeTeamName, String awayTeamName) {

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;

        // TODO: consider adding constructor code that pulls relevant match from database
        retrieveMatchPlayers();
    }

    public Match(String homeTeamName, String awayTeamName, String homePlayer1, String homePlayer2, String awayPlayer1, String awayPlayer2) {

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homePlayer1 = homePlayer1;
        this.homePlayer2 = homePlayer2;
        this.awayPlayer1 = awayPlayer1;
        this.awayPlayer2 = awayPlayer2;
    }

    public void retrieveMatchPlayers() {

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

    // TODO: seperate out setters and sending player to DB
    public void sendHomePlayer1ToDatabase(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET HomePlayer1ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }


    public void sendHomePlayer2ToDatabase(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET HomePlayer2ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }

    public void sendAwayPlayer1ToDatabase(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET AwayPlayer1ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }

    public void sendAwayPlayer2ToDatabase(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.homeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.awayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET AwayPlayer2ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }

    /**********************************************STATIC METHODS******************************************************/

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

    static Match getMatchWithTeams(String homeTeamName, String awayTeamName) {

        // TODO: add getMatchWithTeams()

        return new Match(homeTeamName, awayTeamName);
    }

    /*********************************************GETTERS + SETTERS****************************************************/

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getHomePlayer1() {
        return homePlayer1;
    }

    public void setHomePlayer1(String homePlayer1) {
        this.homePlayer1 = homePlayer1;
    }

    public String getHomePlayer2() {
        return homePlayer2;
    }

    public void setHomePlayer2(String homePlayer2) {
        this.homePlayer2 = homePlayer2;
    }

    public String getAwayPlayer1() {
        return awayPlayer1;
    }

    public void setAwayPlayer1(String awayPlayer1) {
        this.awayPlayer1 = awayPlayer1;
    }

    public String getAwayPlayer2() {
        return awayPlayer2;
    }

    public void setAwayPlayer2(String awayPlayer2) {
        this.awayPlayer2 = awayPlayer2;
    }

    public int getHomeTeamSetsWon() {
        return homeTeamSetsWon;
    }

    public void setHomeTeamSetsWon(int homeTeamSetsWon) {
        this.homeTeamSetsWon = homeTeamSetsWon;
    }

    public int getAwayTeamSetsWon() {
        return awayTeamSetsWon;
    }

    public void setAwayTeamSetsWon(int awayTeamSetsWon) {
        this.awayTeamSetsWon = awayTeamSetsWon;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }
}