

package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AMessageViewHolder extends RecyclerView.ViewHolder {
    //    public User aUser;
    public Message aMessage; // this is a message， one message includes： senderUserName； msgText； msgTime
    public TextView userNameSenderTV, msgTimeTV;
    public ImageView imageView;
    public User logined_user;
    private Context context;

    public AMessageViewHolder(@NonNull View itemView, Context context, User logined_user){
        super(itemView);
        this.context = context;
        this.logined_user = logined_user;
        this.userNameSenderTV = itemView.findViewById(R.id.userName);
        this.msgTimeTV = itemView.findViewById(R.id.msgTime);
        this.imageView = itemView.findViewById(R.id.msgText);
    }

    public void bindSenderMsgTimeMsgText(Message tobeBindedAMessage){
        userNameSenderTV.setText(tobeBindedAMessage.getSenderUserName() + ": ");
        msgTimeTV.setText(tobeBindedAMessage.getMsgTime());
        imageView.setImageResource( Integer.parseInt(tobeBindedAMessage.getMsgText()) );
        aMessage = tobeBindedAMessage;
    }

}