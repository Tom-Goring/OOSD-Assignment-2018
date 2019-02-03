public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team.addTeamToDatabase("UWE");
        Team.addTeamToDatabase("Page");

        Player.addPlayerToDatabase("Tom", "UWE");
        Player.addPlayerToDatabase("Louis", "UWE");
        Player.addPlayerToDatabase("Bob", "Page");
        Player.addPlayerToDatabase("Jun", "Page");

        Set.generateSet("UWE", "Page");
        System.out.println("Pause here.");
    }
}
