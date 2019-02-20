package model;

public class User {

    public static User currentUser;

    private String username;
    private String password;
    private byte[] salt;
    private byte[] hashedPassword;
    final private int privilegeLevel = 1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() { return salt; }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String toString() {
        return "DB_User{" +
                "username='" + username + '\'' +
                ", privilegeLevel=" + privilegeLevel +
                '}';
    }
}

