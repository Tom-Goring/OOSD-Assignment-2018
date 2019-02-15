package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team UWE = new Team("UWE");
        Team Page = new Team("Page");
        Team Filton = new Team("Filton");

        Player Tom = new Player("Tom", UWE);
        Player Louis = new Player("Louis", UWE);
        Player Dave = new Player("Dave", Page);
        Player Bob = new Player("Bob", Page);

        DatabaseManager.Team.insertTeam(UWE);
        DatabaseManager.Team.insertTeam(Page);
        DatabaseManager.Team.insertTeam(Filton);

        DatabaseManager.Player.sendNewPlayerToDB(Tom);
        DatabaseManager.Player.sendNewPlayerToDB(Louis);
        DatabaseManager.Player.sendNewPlayerToDB(Dave);
        DatabaseManager.Player.sendNewPlayerToDB(Bob);

        Fixtures.generateFixtures();
    }
}
