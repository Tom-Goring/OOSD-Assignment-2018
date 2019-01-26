public class Test {

    public static void main(String[] args) {

        Player player = new Player("Not Notson", 1);

        String string = DatabaseAPI.getPlayerWithName("John Johnson"); // eyyyy it works bois

        System.out.println(string);
    }
}
