public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

       DatabaseManager.getPlayersWithTeamID("1").forEach(System.out::println);
    }
}
