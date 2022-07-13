package edu.neu.madscourse.a8stickittoem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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

    public int      smile_count,    kiss_count,     think_count,    wink_count,     expressionless_count,   star_count;
    public TextView smile_count_tv, kiss_count_tv,  think_count_tv, wink_count_tv,  expressionless_count_tv,star_count_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        databaseReference = FirebaseDatabase.getInstance().getReference();
        toUser = getIntent().getStringExtra("toId");
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        sendUser =  sp.getString("name","");
        rvChat =  findViewById(R.id.rv_chat);
        mAdapter = new ChatAdapter(this, list);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        rvChat.setLayoutManager(mLinearLayout);
        rvChat.setAdapter(mAdapter);

        // set find text view for the sticker used count
        smile_count_tv = findViewById(R.id.smile_count);
        kiss_count_tv  = findViewById(R.id.kiss_count);
        think_count_tv = findViewById(R.id.think_count);
        wink_count_tv  = findViewById(R.id.wink_count);
        expressionless_count_tv = findViewById(R.id.expressionless_count);
        star_count_tv  = findViewById(R.id.star_count);

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
                smile_count =0;
                kiss_count  =0;
                think_count =0;
                wink_count  =0;
                expressionless_count =0;
                star_count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message chat = snapshot.getValue(Message.class);
                    upDateStickerCount(chat,sendUser); // call update stickerCount function to compare & update the counts
                    if(chat.getSendName().equals(fromId) && chat.getToName().equals(toId) ||
                            chat.getSendName().equals(toId) && chat.getToName().equals(fromId)){
                        list.add(chat);
                    }
                }
                smile_count_tv.setText("Smile: " + smile_count);
                kiss_count_tv.setText("Kiss: " + kiss_count);
                think_count_tv.setText("Think: " + think_count);

                wink_count_tv.setText("Wink: " + wink_count);
                expressionless_count_tv.setText("Exp.less: " + expressionless_count);
                star_count_tv.setText("Star: " + star_count);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void upDateStickerCount(Message chat, String sendUser){
        if (chat.getSendName().equals(sendUser)){
            switch (chat.getImgId()){
                case "smile":
                    smile_count ++;
                    break;
                case "kiss":
                    kiss_count++;
                    break;
                case "think":
                    think_count++;
                    break;
                case "wink":
                    wink_count++;
                    break;
                case "expressionless":
                    expressionless_count++;
                    break;
                case "star" :
                    star_count++;
                    break;
                default: Log.d(" upDateStickerCount Error: ", "No Match stickers imageID!!!!");
            }
        }
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
    //Back arrow button 后退按键
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

