package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();
        DatabaseManager.Team.sendTeamToDatabase(new Team("UWE"));
        DatabaseManager.Team.sendTeamToDatabase(new Team("Page"));
        DatabaseManager.Team.sendTeamToDatabase(new Team("Filton"));

        DatabaseManager.Player.sendNewPlayerToDB(new Player("Tom", "UWE"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("Louis", "UWE"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("Tim", "UWE"));

        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI1", "Page"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI2", "Page"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI3", "Page"));

        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI4", "Filton"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI5", "Filton"));
        DatabaseManager.Player.sendNewPlayerToDB(new Player("AI6", "Filton"));

        Match UWEvsPage = new Match("UWE", "Page");
        DatabaseManager.Match.sendNewMatchToDB(UWEvsPage);

        DatabaseManager.Match.updateMatchInformation(UWEvsPage);

    }
}
