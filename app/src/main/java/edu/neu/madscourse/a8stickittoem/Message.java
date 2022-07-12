package edu.neu.madscourse.a8stickittoem;

import java.util.Date;


//create model to store the chat in the firebase real-time database
public class Message{
    private String user;
    private long time;
    private String stickerID;

    public Message(String stickerID, String user) {
       this.stickerID=stickerID;
       this.user=user;
       time=new Date().getTime();
    }

    public Message() {
    }

    public String getStickerID() {
        return stickerID;
    }

    public void setStickerID(String stickerID) {
        this.stickerID=stickerID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user=user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
