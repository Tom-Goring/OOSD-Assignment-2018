package model;

public class Admin extends User {

    final private int privilegeLevel = 2;

    @Override
    public String toString() {
        return "Admin: " +
                "Username='" + super.getUsername() + '\'' +
                ' ';
    }
}
