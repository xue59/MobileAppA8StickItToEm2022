package edu.neu.madscourse.a8stickittoem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    public static String sendUser ="";
    public static String toUser ="";
    private DatabaseReference chatRef;
    private List<Message> list = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ChatAdapter mAdapter;
    private RecyclerView rvChat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        toUser = getIntent().getStringExtra("toId");
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        sendUser =  sp.getString("name","");
        rvChat =  findViewById(R.id.rv_chat);

        mAdapter = new ChatAdapter(this, list);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        rvChat.setLayoutManager(mLinearLayout);
        rvChat.setAdapter(mAdapter);


        getData(sendUser, toUser);
        findViewById(R.id.iv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText(checkId);
            }
        });
        ic_smile = findViewById(R.id.ic_smile);
        ic_smile .setOnClickListener(this);
        ic_kiss =  findViewById(R.id.ic_kiss);
        ic_kiss .setOnClickListener(this);
        ic_think =findViewById(R.id.ic_think);
        ic_think.setOnClickListener(this);
        ic_wink= findViewById(R.id.ic_wink);
        ic_wink.setOnClickListener(this);
        ic_expressionless =findViewById(R.id.ic_expressionless);
        ic_expressionless.setOnClickListener(this);
        ic_star = findViewById(R.id.ic_star);
        ic_star.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        clearSelect();
        if (view.getId()==R.id.ic_smile){
            checkId = "smile";
            ic_smile.setBackgroundColor(getColor(R.color.colorCCC));
        }else if (view.getId()==R.id.ic_kiss){
            checkId = "kiss";
            ic_kiss.setBackgroundColor(getColor(R.color.colorCCC));
        }else if (view.getId()==R.id.ic_think){
            checkId = "think";
            ic_think.setBackgroundColor(getColor(R.color.colorCCC));
        }else if (view.getId()==R.id.ic_wink){
            checkId = "wink";
            ic_wink.setBackgroundColor(getColor(R.color.colorCCC));
        }else if (view.getId()==R.id.ic_expressionless){
            checkId = "expressionless";
            ic_expressionless.setBackgroundColor(getColor(R.color.colorCCC));
        }else if (view.getId()==R.id.ic_star){
            checkId = "star";
            ic_star.setBackgroundColor(getColor(R.color.colorCCC));
        }
    }
    private ImageView ic_smile;
    private ImageView ic_kiss;
    private ImageView ic_think;
    private ImageView ic_wink;
    private ImageView ic_expressionless;
    private ImageView ic_star;
    private void clearSelect() {
        ic_smile .setBackgroundColor(getColor(R.color.white));
        ic_kiss .setBackgroundColor(getColor(R.color.white));
        ic_think .setBackgroundColor(getColor(R.color.white));
        ic_wink .setBackgroundColor(getColor(R.color.white));
        ic_expressionless .setBackgroundColor(getColor(R.color.white));
        ic_star .setBackgroundColor(getColor(R.color.white));
    }

    private String checkId = "";



    private void getData(final String fromId, final String toId ){

        chatRef = FirebaseDatabase.getInstance().getReference("MessageHistory");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message chat = snapshot.getValue(Message.class);
                    if(chat.getSendName().equals(fromId) && chat.getToName().equals(toId) ||
                            chat.getSendName().equals(toId) && chat.getToName().equals(fromId)){
                        list.add(chat);
                    }


                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendText(String msg) {
        if (TextUtils.isEmpty(msg)){
            return;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sendName", sendUser);
        hashMap.put("imgId", msg);
        hashMap.put("toName", toUser);
        hashMap.put("timestamp", System.currentTimeMillis());
        databaseReference.child("MessageHistory").push().setValue(hashMap);

    }


}

