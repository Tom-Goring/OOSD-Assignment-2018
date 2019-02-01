// TODO: add Match class
public class Match {

    // Home Team
    // Away Team
    // Winner
    // Array / List of Sets

    static void createMatch(int HTID, int ATID) {

        // send to database: HomeTeamID & AwayTeamID (using their names i suppose)
        // Players select after game is played? I dont actually know
        // Winner is added after game is played (naturally)
        String insert = "INSERT INTO `Match` (HomeTeamID, AwayTeamID) VALUES (";

        insert += HTID + ", " + ATID  + ")";

        DatabaseManager.insertData(insert);

        // createMatchSets
    }
}
