package model;

public class Admin extends User {

    @Override
    public String toString() {
        return "Admin: " +
                "Username='" + getUsername() + '\'' +
                ' ';
    }
}
