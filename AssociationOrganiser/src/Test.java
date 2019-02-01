public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        DatabaseManager.addTeamToDatabase("UWE");
        DatabaseManager.addTeamToDatabase("Page");
        DatabaseManager.addTeamToDatabase("Filton");

        DatabaseManager.addPlayerToDatabase("Tom", "UWE");
        DatabaseManager.addPlayerToDatabase("Louis", "UWE");
        DatabaseManager.addPlayerToDatabase("Tim", "UWE");
        DatabaseManager.addPlayerToDatabase("Bob", "Page");
        DatabaseManager.addPlayerToDatabase("Jun", "Page");

        DatabaseManager.generateFixtures();

        System.out.println("Pause here.");
    }
}
