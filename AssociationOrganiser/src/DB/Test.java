package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team uwe = new Team("UWE");
        DatabaseManager.Team.sendTeamToDatabase(uwe);

        DatabaseManager.Player.sendNewPlayerToDB(new Player("Tom", "UWE"));
    }
}
