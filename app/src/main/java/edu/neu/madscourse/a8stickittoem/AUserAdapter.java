package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AUserAdapter extends RecyclerView.Adapter<AUserViewHolder> {

    List<User> listOfUsers;
    Context context;
    User logined_user;

    public AUserAdapter(List<User> listOfUsers, Context context, User logined_user){
        this.listOfUsers = listOfUsers;
        this.logined_user = logined_user;
        this.context = context;

    }

    @Override
    @NonNull
    public  AUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AUserViewHolder(LayoutInflater.from(context).inflate(R.layout.a_user_view_holder, parent, false), context, this.logined_user);
    }

    @Override
    public void onBindViewHolder(@NonNull AUserViewHolder holder, int position){
        holder.bindUserNameUserID(listOfUsers.get(position));
    }

    public void updateList(List<User> newList) {
        this.listOfUsers = newList;
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount(){
        return listOfUsers.size();
    }

}
