import java.util.ArrayList;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        DatabaseManager.addTeamToDatabase("UWE");

        DatabaseManager.addPlayerToDatabase("Tom", "UWE");

        ArrayList<Player> playerList = new ArrayList<>();

        playerList = DatabaseManager.getPlayerWithName("Tom");

        for (Player player : playerList) {

            System.out.println(player.toString());
        }

        playerList = DatabaseManager.getPlayersWithTeamName("UWE");

        for (Player player : playerList) {

            System.out.println(player.toString());
        }
    }
}
