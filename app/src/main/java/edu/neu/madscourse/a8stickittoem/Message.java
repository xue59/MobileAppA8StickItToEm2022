package edu.neu.madscourse.a8stickittoem;

import java.text.SimpleDateFormat;
import java.util.Date;


//create model to store the a message in the firebase real-msgTime database
public class Message{
    private String senderUserName;
    private String msgTime;
    private String msgText;

    public Message(String senderUserName, String msgText) {
       this.msgText=msgText;
       this.senderUserName=senderUserName;
       String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
       this.msgTime= timeStamp;

    }


    public String getMsgText() {
        return msgText;
    }


    public String getSenderUserName() {
        return senderUserName;
    }

    public long getMsgTime() {
        return (toString(msgTime));
    }

    private long toString(long msgTime) {
    }

}
