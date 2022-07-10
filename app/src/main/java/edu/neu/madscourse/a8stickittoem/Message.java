package edu.neu.madscourse.a8stickittoem;

import java.time.LocalDateTime;

public class Message {

    public LocalDateTime myDT;
    public String emojiID;
    public User sender;
    public User receiver;

    public Message() {}

    public Message(LocalDateTime myDT, String emojiID, User sender, User receiver) {
        this.myDT = myDT;
        this.emojiID = emojiID;
        this.sender = sender;
        this.receiver = receiver;
    }

    public LocalDateTime getMyDT() {
        return myDT;
    }

    public String getEmojiID() {
        return emojiID;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }
}
