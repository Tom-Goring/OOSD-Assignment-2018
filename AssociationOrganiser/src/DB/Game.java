package DB;

// TODO: add Game class
public class Game {

    int homeTeamScore;
    int awayTeamScore;
    boolean complete;

    /*static void generateGames() {

        String getSets = "SELECT ID FROM `Set`";

        ArrayList<String[]> sets = DatabaseManager.executeQuery(getSets);

        for (String[] set : sets) {

            // TODO: consider making gameNumber a class variable
            for (int gameNumber = 0; gameNumber < 3; gameNumber++) {

                createGame(Integer.parseInt(set[0]), gameNumber+1);
            }
        }
    }

    private static void createGame(int parentSet, int gameNumber) {

        String insert = "INSERT INTO Game (SetID, GameNumber) VALUES (" + parentSet + ", " + gameNumber + ")";

        DatabaseManager.insertData(insert);
    }*/
}
