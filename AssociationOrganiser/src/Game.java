import java.util.ArrayList;

// TODO: add Game class
public class Game {

    // Home Player
    // Away Player
    // Home Score
    // Away Score
    // Winner?

    static void generateGames() {

        String getSets = "SELECT ID FROM `Set`";

        ArrayList<String[]> sets = DatabaseManager.executeQuery(getSets);

        for (String[] set : sets) {

            for (int i = 0; i < 3; i++) {

                createGame(Integer.parseInt(set[0]));
            }
        }
    }

    private static void createGame(int parentSet) {

        String insert = "INSERT INTO Game (SetID) VALUES (" + parentSet + ")";

        DatabaseManager.insertData(insert);
    }
}
