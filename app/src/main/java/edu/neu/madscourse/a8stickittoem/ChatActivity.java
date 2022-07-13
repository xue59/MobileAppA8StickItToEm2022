package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private int selectedPosition;
    private DatabaseReference mRootRef;
    public User logined_user;
    public String chating_with;
    public final int[] images = {R.drawable.ic_smile, R.drawable.ic_kiss, R.drawable.ic_think,
            R.drawable.ic_wink, R.drawable.ic_expressionless, R.drawable.ic_star};
    public String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here
        mRootRef = FirebaseDatabase.getInstance().getReference(); // Get root ref of database
        chating_with = getIntent().getStringExtra("chating_with"); // Retrieve chatID
        logined_user = (User) getIntent().getSerializableExtra("logined_user"); // Retrieve logined user (username & uid)
        checkChatID(mRootRef);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushToDb();
            }
        });

        GridAdaptor gridAdaptor = new GridAdaptor(this, images);
        GridView gv = findViewById(R.id.grid_sticker);
        gv.setAdapter(gridAdaptor);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridAdaptor.setSelectedPosition(position);
                selectedPosition = position;
                gridAdaptor.notifyDataSetChanged();
            }
        });
    }


    // Backup button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Push message to database
    public void pushToDb() {

        // Using timestamp as key
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date()).replace(".", "");
        DatabaseReference messageRef = mRootRef.child("chatHistory").child(chatID).child(timeStamp).getRef();
        String senderUserName = logined_user.getUsername();
        String msgText = String.valueOf(images[selectedPosition]);
        String msgTime = DateFormat.format("MM-dd-yyyy HH:mm", new Date().getTime()).toString();
        messageRef.setValue(new Message(senderUserName, msgText, msgTime));
    }

    //following function used to check if chatID exist in the database
    //otherwies creat a new chatID, storeChatID in ChatID
    public void checkChatID(DatabaseReference mRootRef){
        mRootRef.child("chatHistory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override  // used for determing which chatID exist in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Try found chat_ID: ", logined_user.getUsername() +chating_with +" "+ snapshot.toString());
                if (snapshot.hasChild(logined_user.getUsername() +"_"+chating_with)){
                    chatID = logined_user.getUsername() +"_"+chating_with;
                }else{
                    if(snapshot.hasChild(chating_with +"_"+ logined_user.getUsername())){
                        chatID = chating_with +"_"+ logined_user.getUsername();
                    } else {
                        Log.d("Error no chat id found: ", "Creating New Chat_ID");
                        chatID = logined_user.getUsername() +"_"+chating_with;;
                        mRootRef.child("chatHistory").child(chatID).setValue("");
                    }
                }
                Log.d("Found chat ID: ", chatID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}