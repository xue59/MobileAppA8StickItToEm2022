package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<Message> adapter; // Using firebaseUI library function
    private DatabaseReference mRootRef;
    public User logined_user;
    public List<Message> listOfMessages;
    public RecyclerView messagesRecyclerView;
    public String chating_with;
    public String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here 开始这里coding
        mRootRef = FirebaseDatabase.getInstance().getReference(); // Get root ref of database
        checkChatID(mRootRef);
        chating_with = getIntent().getStringExtra("chating_with"); // Retrieve chatID
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new Message(stickerID, username)
                        );
            }
        });
        */

        //zack's code for diplaying chat message history:
        logined_user = (User) getIntent().getSerializableExtra("logined_user"); // Retrieve logined user (username & uid)
//        Log.d("Chating with: ", chating_with);
//        Log.d("Chat Login with: ", logined_user.getUsername());
        Toast.makeText(getApplicationContext(), "Chatting with " + chating_with, Toast.LENGTH_LONG)
                .show();


        Log.d("Current chat ID: ", chatID);

        listOfMessages = new ArrayList<>(); // when creating message: public Message(String senderUserName, String msgText)
//        listOfMessages.add(new Message("Wang", "Hello from Wang"));
//        listOfMessages.add(new Message("Zack", "Hello from Zack"));
//        listOfMessages.add(new Message("Wang", "How R U from Wang"));
        messagesRecyclerView = findViewById(R.id.chat_messages_recycle_view);
        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRootRef.child("chatHistory").child(chatID).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        Log.d("onChildAdded chathistory: ", snapshot.getValue());
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


//        messagesRecyclerView.setAdapter(new AChatMessagesAdapter(listOfMessages, ChatActivity.this, logined_user));
//        Log.d("chatActivity listMessages: ", listOfMessages.get(1).getSenderUserName() + listOfMessages.get(1).getMsgTime() + listOfMessages.get(1).getMsgText());
//        Log.d("chatActivity listMessages: ", listOfMessages.get(2).getSenderUserName() + listOfMessages.get(2).getMsgTime() + listOfMessages.get(2).getMsgText());
        // connect to Firebase rea-time database
        // Victor already called: mRootRef = FirebaseDatabase.getInstance().getReference(); // Get root ref of database







        int[] images = {R.drawable.ic_smile, R.drawable.ic_kiss, R.drawable.ic_think,
                R.drawable.ic_wink, R.drawable.ic_expressionless, R.drawable.ic_star};
        GridAdaptor gridAdaptor = new GridAdaptor(this, images);
        GridView gv = findViewById(R.id.grid_sticker);
        gv.setAdapter(gridAdaptor);
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


    //holding code:
    public void displayMessage() {
        /*
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message, mRootRef) {
            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
     */
    }

    //后退按键
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