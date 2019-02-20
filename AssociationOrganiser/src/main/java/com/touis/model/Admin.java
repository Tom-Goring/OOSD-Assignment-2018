package main.java.com.touis.model;

public class Admin extends User {

    final private int privilegeLevel = 2;

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + super.getUsername() + '\'' +
                ", privilegeLevel=" + privilegeLevel +
                '}';
    }
}
