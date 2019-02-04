import java.util.ArrayList;

public class Match {

    // TODO: add instance and class variables + methods for Match class

    private String HomeTeamName;
    private String AwayTeamName;

    // TODO: consider making these actual player classes
    private String HomePlayer1;
    private String HomePlayer2;
    private String AwayPlayer1;
    private String AwayPlayer2;

    int HomeTeamSetsWon;
    int AwayTeamSetsWon;

    String WinningTeam;

    public Match(String homeTeamName, String awayTeamName) {

        this.HomeTeamName = homeTeamName;
        this.AwayTeamName = awayTeamName;

        // TODO: consider adding constructor code that pulls relevant match from database
        retrieveMatchPlayers();
    }

    public void retrieveMatchPlayers() {

        ArrayList<String[]> data = new ArrayList<>();

        String getHID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + ")";
        String getAID = "(SELECT ID FROM Team WHERE Name = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName) + ")";
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
        this.HomePlayer1 = data.get(0)[0];
        this.HomePlayer2 = data.get(0)[1];
        this.AwayPlayer1 = data.get(0)[2];
        this.AwayPlayer2 = data.get(0)[3];
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

    /**********************************************GETTERS + SETTERS*******************************************************/

    // TODO: seperate out setters and sending player to DB
    public void setHomePlayer1(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET HomePlayer1ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }


    public void setHomePlayer2(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET HomePlayer2ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }

    public void setAwayPlayer1(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET AwayPlayer1ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }

    public void setAwayPlayer2(String playerName) {

        String getHTID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + "))";
        String getATID = "(SELECT ID FROM Team WHERE (Name = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName) + "))";
        String getPID = "(SELECT ID FROM Player WHERE (Name = ("+ DatabaseManager.surroundWithQuotes(playerName) +")))";
        String getMatchID = "(SELECT ID FROM `Match` WHERE (HomeTeamID = ("+ getHTID +") AND AwayTeamID = ("+ getATID +")))";
        String update = "UPDATE `Match` SET AwayPlayer2ID = ("+ getPID +") WHERE (ID = ("+ DatabaseManager.executeQuery(getMatchID).get(0)[0] +") );";

        DatabaseManager.insertData(update);
    }
}