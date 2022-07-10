package edu.neu.madscourse.a8stickittoem;

public class User {

    public String username;
    public String userID;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String username) {
        this.username = username;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }
}