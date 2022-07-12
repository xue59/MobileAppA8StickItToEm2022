package edu.neu.madscourse.a8stickittoem;

import java.util.Date;


//create model to store the a message in the firebase real-msgTime database
public class Message{
    private String senderUserName;
    private long msgTime;
    private String msgText;

    public Message(String senderUserName, String msgText) {
       this.msgText=msgText;
       this.senderUserName=senderUserName;
       this.msgTime=new Date().getTime();
    }


    public String getMsgText() {
        return msgText;
    }


    public String getSenderUserName() {
        return senderUserName;
    }

    public long getMsgTime() {
        return msgTime;
    }

}
