package DB;

import java.util.ArrayList;

// TODO: add Set class
public class Set {

    static void generateSets() {

        // get all matches
        String getMatches = "SELECT ID FROM `Match`";

        ArrayList<String[]> matches = DatabaseManager.executeQuery(getMatches);

        for (String[] match : matches) {

            for (int setNumber = 0; setNumber < 5; setNumber++) {

                createSet(Integer.parseInt(match[0]), setNumber+1);
            }
        }
    }

    private static void createSet(int parentMatch, int setNumber) {

        //TODO: replace HomeTeamName and AwayTeamName with references to TeamID's
        String insert = "INSERT INTO `Set` (MatchID, SetNumber) VALUES (" + parentMatch + ", " + setNumber + ")";

        DatabaseManager.insertData(insert);
    }

    public static void updateMatchPlayers(String HomePlayer1Name, String HomePlayer2Name, String AwayPlayer1Name, String AwayPlayer2Name) {

        // TODO: add updateMatchPlayers (and also an updateMatchScores?) also think about passing in a Match class here
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // TODO: method to retrieve all the data about a set?
}
