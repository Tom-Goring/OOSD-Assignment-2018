package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        //DatabaseManager.createTables();

        Team UWE = new Team("UWE");
        Team Page = new Team("Page");

        Player Tom = new Player("Tom", UWE);
        Player Louis = new Player("Louis", UWE);
        Player Dave = new Player("Dave", Page);
        Player Bob = new Player("Bob", Page);

        DatabaseManager.Team.insertTeam(UWE);
        DatabaseManager.Team.insertTeam(Page);

        DatabaseManager.Player.sendNewPlayerToDB(Tom);
        DatabaseManager.Player.sendNewPlayerToDB(Louis);
        DatabaseManager.Player.sendNewPlayerToDB(Tom);
        DatabaseManager.Player.sendNewPlayerToDB(Bob);

        Match match = new Match(UWE, Page);

        System.out.println(match.getHomeTeam().getPlayerList());
        System.out.println(match.getAwayTeam().getPlayerList());

        match.setHomeTeamPlayer1(Tom);
        match.setHomeTeamPlayer2(Louis);
        match.setAwayTeamPlayer1(Dave);
        match.setAwayTeamPlayer2(Bob);

        System.out.println(match.getMatchPlayers());

        match.getSet(1).setHomeTeamPlayer(Tom);
        match.getSet(1).setAwayTeamPlayer(Dave);

        match.getSet(1).getGame(1).setHomeTeamScore(2);
        match.getSet(1).getGame(1).setAwayTeamScore(1);

        System.out.println(match.getGameScore(1, 1));
    }
}
