public class Test {

    public static void main(String[] args) {

        Player player = new Player("Not Notson", 1);

        String string = Player.getPlayerWithName("John Johnson");

        System.out.println(string);
    }
}
