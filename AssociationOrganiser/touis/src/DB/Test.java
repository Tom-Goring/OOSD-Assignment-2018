package DB;

import model.User;

public class Test {

    // Just a class to allow for some basic testing of functionality before main GUI is implemented
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            User user = new User();
            user.setUsername("testUser " + i);
            user.setPassword("password");
            user.setSalt(DatabaseManager.DB_Security.generateSalt());
            user.setHashedPassword(DatabaseManager.DB_Security.hashPassword(user.getPassword(), user.getSalt()));

            if (user.getHashedPassword() != null) {

                if (!DatabaseManager.DB_User.addPlayerToDatabase(user)) {

                    System.out.println("duplicate placeholder");
                }
            }
        }
    }
}
