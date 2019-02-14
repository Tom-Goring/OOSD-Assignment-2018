package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();
        DatabaseManager.Team.sendTeamToDatabase(new Team("UWE"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("Tom", "UWE"));

        Player tom = DatabaseManager.Player.loadPlayerInformation("Tom");
        System.out.println(tom.getPlayerName());
        System.out.println(tom.getTeamName());
    }
}
