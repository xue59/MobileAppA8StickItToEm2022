package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AUserViewHolder extends RecyclerView.ViewHolder {
    public User aUser;
    public TextView userNameTV, userIDTV;
    public Button btnChat;
    public User logined_user;
    private Context context;



    public AUserViewHolder(@NonNull View itemView, Context context, User logined_user){
        super(itemView);
        this.userNameTV = itemView.findViewById(R.id.userName);
        this.userIDTV   = itemView.findViewById(R.id.userID);
        this.context  = context;
        this.logined_user = logined_user;
        btnChat = itemView.findViewById(R.id.btnChat);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Go to Chat: ", "clicked " + logined_user.getUsername()+"_"+aUser.getUsername());
                Toast.makeText(context.getApplicationContext(), "Jump to chat " + logined_user.getUsername()+"_"+aUser.getUsername(), Toast.LENGTH_LONG)
                        .show();
                // new intent
                // putextra(sender, receiver)
                // startactivity(messageActivity.class)

            }
        });
    }

    public void bindUserNameUserID(User tobeBindedUser){
        userNameTV.setText(tobeBindedUser.getUsername());
        userIDTV.setText(tobeBindedUser.getUserID());
        aUser = tobeBindedUser;
    }
}
