package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<Message> msgList;
    Context context ;

    public ChatAdapter(Context context, List<Message> msgList){
        this.msgList = msgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.a_message_view_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = msgList.get(position);
        holder.userName.setText(msg.getSendName());
        holder.msgTime.setText(formatTime(msg.getTimestamp()));
        if (msg.getImgId().equals("smile")){
            holder.ivPic.setImageResource(R.drawable.ic_smile);
        }else if (msg.getImgId().equals("kiss")){
            holder.ivPic.setImageResource(R.drawable.ic_kiss);
        }else if (msg.getImgId().equals("think")){
            holder.ivPic.setImageResource(R.drawable.ic_think);
        }else if (msg.getImgId().equals("wink")){
            holder.ivPic.setImageResource(R.drawable.ic_wink);
        }else if (msg.getImgId().equals("expressionless")){
            holder.ivPic.setImageResource(R.drawable.ic_expressionless);
        }else if (msg.getImgId().equals("star")){
            holder.ivPic.setImageResource(R.drawable.ic_star);
        }

    }

    public String formatTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        return simpleDateFormat.format(new Date(time));
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView msgTime;
        ImageView ivPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            msgTime = itemView.findViewById(R.id.msgText);
            ivPic = itemView.findViewById(R.id.iv_status);

        }
    }

}

