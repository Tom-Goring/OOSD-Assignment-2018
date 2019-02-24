package model;

public class BasicUser extends User {

    @Override
    public String toString() {
        return "BasicUser: " +
                "Username='" + getUsername() + '\'' +
                ' ';
    }
}

