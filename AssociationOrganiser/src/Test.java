public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team.addTeamToDatabase("UWE");
        Team.addTeamToDatabase("Page");
        Team.addTeamToDatabase("Filton");

        Player.addPlayerToDatabase("Tom", "UWE");
        Player.addPlayerToDatabase("Louis", "UWE");
        Player.addPlayerToDatabase("Tim", "UWE");
        Player.addPlayerToDatabase("Bob", "Page");
        Player.addPlayerToDatabase("Jun", "Page");

        Fixtures.generateFixtures();

        System.out.println("Pause here.");
    }
}
