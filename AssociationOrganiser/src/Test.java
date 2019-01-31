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

        DatabaseManager.createMatch(
                DatabaseManager.getTeamWithName("UWE").getTeamID(), // Home Team
                DatabaseManager.getTeamWithName("Page").getTeamID(), // Away Team
                DatabaseManager.getPlayerWithName("Tom").getPlayer_id(), // HP1
                DatabaseManager.getPlayerWithName("Louis").getPlayer_id(), // HP2
                DatabaseManager.getPlayerWithName("Bob").getPlayer_id(), // AP1
                DatabaseManager.getPlayerWithName("Jun").getPlayer_id() ); // AP2

        System.out.println("Pause here.");
    }
}
