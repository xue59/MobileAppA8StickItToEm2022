package edu.neu.madscourse.a8stickittoem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<UserModel> list = new ArrayList<>();
    private String userName;
    private DatabaseReference dataRef;
    private NotificationManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        recyclerView = findViewById(R.id.all_users_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this,list);
        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String toId = list.get(position).getName();
                Intent intent = new Intent(HomeActivity.this,ChatActivity.class);
                intent.putExtra("toId",toId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        userName =  sp.getString("name","");
        getUser();
        checkNotice();
    }

    boolean isFirst;
    private void checkNotice() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("notice","chat",NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(notificationChannel);
        }
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("MessageHistory");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message chat = snapshot.getValue(Message.class);
                    if(chat.getToName().equals(userName) ){
                        noticeMsg(chat);
                    }
                }
                isFirst = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private long latTime;
    private void noticeMsg(Message chat) {
        if (latTime>chat.getTimestamp()){
            return;
        }
        if (isFirst){
            if (latTime<chat.getTimestamp()){
                latTime = chat.getTimestamp();
            }
            return;
        }
        Intent intent = new Intent(this,HomeActivity.class);
        // 加上PendingIntent之后，点击通知就会弹出到另一个layout中
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        Notification notification = new NotificationCompat.Builder(this,"notice")
                .setContentTitle("A8 Stick It To 'Em")
                .setContentText("you got a new emoji")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.email)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.email))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }

    private void getUser() {
        dataRef = FirebaseDatabase.getInstance().getReference().child("User");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if (!userModel.getName().equals(userName)){
                        list.add(userModel);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
