package DB;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        DatabaseManager.createTables();

        Team UWE = new Team("UWE");
        Team Page = new Team("Page");
        Team Filton = new Team("Filton");

        Match match = new Match(UWE, Page);
    }
}
