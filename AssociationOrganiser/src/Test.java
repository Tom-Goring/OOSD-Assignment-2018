public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        DatabaseManager.addTeamToDatabase("UWE");

        DatabaseManager.addPlayerToDatabase("Tom", "UWE");
        DatabaseManager.addPlayerToDatabase("Louis", "UWE");
        DatabaseManager.addPlayerToDatabase("Tim", "UWE");

        for (Player player : DatabaseManager.getPlayersWithTeamName("UWE")) {

            System.out.println(player.toString());
        }
    }
}
