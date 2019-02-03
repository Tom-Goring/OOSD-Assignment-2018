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

    public Match(String homeTeamName, String awayTeamName,
                 String homePlayer1, String homePlayer2,
                 String awayPlayer1, String awayPlayer2) {

        HomeTeamName = homeTeamName;
        AwayTeamName = awayTeamName;
        HomePlayer1 = homePlayer1;
        HomePlayer2 = homePlayer2;
        AwayPlayer1 = awayPlayer1;
        AwayPlayer2 = awayPlayer2;
    }

    // Placeholder
    public Match(String homeTeamName, String awayTeamName) {

        this.HomeTeamName = homeTeamName;
        this.AwayTeamName = awayTeamName;

        // TODO: consider adding constructor code that pulls relevant match from database
    }

    // TODO: finish updateMatchPlayers - add proper WHERE statement
    public void updateMatchPlayers() {

        String WHERE = "WHERE (HomeTeamName = " + DatabaseManager.surroundWithQuotes(this.HomeTeamName) + " AND "
                + "AwayTeamName = " + DatabaseManager.surroundWithQuotes(this.AwayTeamName);

        ArrayList<String> commandList = new ArrayList<>();

        commandList.add("UPDATE `Match` SET HomePlayer1ID = " + this.HomePlayer1 + ";");
        commandList.add("UPDATE `Match` SET HomePlayer2ID = " + this.HomePlayer2 + ";");
        commandList.add("UPDATE `Match` SET AwayPlayer1ID = " + this.AwayPlayer1 + ";");
        commandList.add("UPDATE `Match` SET AwayPlayer2ID = " + this.AwayPlayer2 + ";");

        for (String command : commandList) {

            DatabaseManager.insertData(command);
        }
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

    public void setHomePlayer1(String homePlayer1) {
        HomePlayer1 = homePlayer1;
    }

    public void setHomePlayer2(String homePlayer2) {
        HomePlayer2 = homePlayer2;
    }

    public void setAwayPlayer1(String awayPlayer1) {
        AwayPlayer1 = awayPlayer1;
    }

    public void setAwayPlayer2(String awayPlayer2) {
        AwayPlayer2 = awayPlayer2;
    }
}