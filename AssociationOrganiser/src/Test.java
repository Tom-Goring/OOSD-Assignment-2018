public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        /*DatabaseManager.createTables();

        Team.addTeamToDatabase("UWE");
        Team.addTeamToDatabase("Page");
        Team.addTeamToDatabase("Filton");

        Player.addPlayerToDatabase("Tom", "UWE");
        Player.addPlayerToDatabase("Louis", "UWE");
        Player.addPlayerToDatabase("Bob", "Page");
        Player.addPlayerToDatabase("Jun", "Page");
        Player.addPlayerToDatabase("John", "Filton");
        Player.addPlayerToDatabase("Frank", "Filton");

        Fixtures.generateFixtures();*/

        Match match = new Match("UWE" , "Page");

        match.setHomePlayer1("Tom");
        match.setHomePlayer2("Louis");
        match.setAwayPlayer1("Bob");
        match.setAwayPlayer2("Jun");

        Match match2 = new Match("UWE", "Page");

        System.out.println("Pause here.");


    }
}
