package edu.neu.madscourse.a8stickittoem;

import java.util.Date;

public class Message {

    private String stickerID;
    private String messageUser;
    private long messageTime;

    public Message(String messageText, String messageUser) {
        this.stickerID = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public Message() {

    }

    public String getMessageText() {
        return stickerID;
    }

    public void setMessageText(String messageText) {
        this.stickerID = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
