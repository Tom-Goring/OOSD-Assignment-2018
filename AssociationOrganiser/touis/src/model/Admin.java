package model;

public class Admin extends User {

    final static private int privilegeLevel = 1;

    public int getPrivilegeLevel() { return privilegeLevel; }

    @Override
    public String toString() {
        return "Admin: " +
                "Username='" + super.getUsername() + '\'' +
                ' ';
    }
}
