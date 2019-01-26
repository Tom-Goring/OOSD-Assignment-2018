public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        String string = DatabaseManager.getPlayerWithName("John Johnson");

        String string2 = DatabaseManager.getPlayersWithTeamID("1");

        System.out.println(string2);
    }
}
