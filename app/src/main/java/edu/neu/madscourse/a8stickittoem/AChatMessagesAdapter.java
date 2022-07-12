package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
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
