package DB;

import model.Fixtures;
import model.Player;
import model.Team;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team UWE = new Team("UWE");
        Player Tom = new Player("Tom");
        Player Louis = new Player("Louis");
        Player Tim = new Player("Tim");

        DatabaseManager.Team.addNewTeamToDatabase(UWE);
        DatabaseManager.Player.addNewPlayerToDatabase(Tom, UWE);
        DatabaseManager.Player.addNewPlayerToDatabase(Louis, UWE);
        DatabaseManager.Player.addNewPlayerToDatabase(Tim, UWE);

        Team test = DatabaseManager.Team.getTeamFromDatabase("UWE");

        System.out.println(test.getPlayerList());

        Fixtures.generateFixtures();
    }
}
