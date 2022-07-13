package edu.neu.madscourse.a8stickittoem;

import android.text.format.DateFormat;

import java.util.Date;


//create model to store the a message in the firebase real-msgTime database
public class Message{
    private String senderUserName;
    private String msgTime;
    private String msgText;

    public Message(String senderUserName, String msgText, String msgTime) {
       this.msgText = msgText;
       this.senderUserName = senderUserName;
       this.msgTime = msgTime;
    }

    public String getMsgText() {
        return msgText;
    }


    public String getSenderUserName() {
        return senderUserName;
    }

    public String getMsgTime() {
        return msgTime;
    }



}
