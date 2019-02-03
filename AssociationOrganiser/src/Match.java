public class Match {

    // TODO: add instance and class variables + methods for Match class

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
}
