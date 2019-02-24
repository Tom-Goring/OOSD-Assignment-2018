package model;

public class BasicUser extends User {

    final static private int privilegeLevel = 1;

    public static int getPrivilegeLevel() { return privilegeLevel; }

    @Override
    public String toString() {
        return "BasicUser: " +
                "Username='" + getUsername() + '\'' +
                ' ';
    }
}

