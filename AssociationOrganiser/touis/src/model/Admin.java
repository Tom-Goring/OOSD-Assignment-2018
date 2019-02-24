package model;

public class Admin extends User {

    final static private int privilegeLevel = 2;

    public static int getPrivilegeLevel() { return privilegeLevel; }

    @Override
    public String toString() {
        return "Admin: " +
                "Username='" + getUsername() + '\'' +
                ' ';
    }
}
