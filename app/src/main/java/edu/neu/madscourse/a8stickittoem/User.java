package edu.neu.madscourse.a8stickittoem;


import java.io.Serializable;

public class User implements Serializable {

    String username;
    String userID;

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
