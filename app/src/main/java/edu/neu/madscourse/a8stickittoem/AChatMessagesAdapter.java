package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AChatMessagesAdapter extends RecyclerView.Adapter<AMessageViewHolder> {

    List<Message> listOfMessages;
    Context context;
    User logined_user;


    public AChatMessagesAdapter(List<Message> listOfMessages, ChatActivity context, User logined_user) {
        this.listOfMessages = listOfMessages;
//        Log.d("AChatMessagesAdapter listMessages: ", listOfMessages.get(1).getSenderUserName() + listOfMessages.get(1).getMsgTime() + listOfMessages.get(1).getMsgText());
//        Log.d("AChatMessagesAdapter listMessages: ", listOfMessages.get(2).getSenderUserName() + listOfMessages.get(2).getMsgTime() + listOfMessages.get(2).getMsgText());
        this.logined_user   = logined_user;
        this.context = context;
    }

    @Override
    @NonNull
    public  AMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.a_message_view_holder, parent, false), context, this.logined_user);
    }

    @Override
    public void onBindViewHolder(@NonNull AMessageViewHolder holder, int position) {
        holder.bindSenderMsgTimeMsgText(listOfMessages.get(position));
    }

    public void updateMessagesList(List<Message> newList) {
        this.listOfMessages = newList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return listOfMessages.size();
    }
}
