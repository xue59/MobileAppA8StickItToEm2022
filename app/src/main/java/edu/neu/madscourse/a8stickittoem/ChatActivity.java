package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here 开始这里coding
        mRootRef = FirebaseDatabase.getInstance().getReference(); // Get root ref of database
        String chatID = getIntent().getStringExtra("chatID"); // Retrieve chatID
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
        Log.d("Chating with: ", chatID);
        Log.d("Chat Login with: ", logined_user.getUsername());
        Toast.makeText(getApplicationContext(), "Chatting with " + chatID, Toast.LENGTH_LONG)
                .show();
        listOfMessages = new ArrayList<>(); // when creating message: public Message(String senderUserName, String msgText)
        listOfMessages.add(new Message("Wang", "Hello from Wang"));
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } // set sleep 1s
        listOfMessages.add(new Message("Zack", "Hello from Zack"));
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } // set sleep 1s
        listOfMessages.add(new Message("Wang", "How R U from Wang"));

        messagesRecyclerView = findViewById(R.id.chat_messages_recycle_view);
        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(new AChatMessagesAdapter(listOfMessages, ChatActivity.this, logined_user));
        Log.d("chatActivity listMessages: ", listOfMessages.get(1).getSenderUserName() + listOfMessages.get(1).getMsgTime() + listOfMessages.get(1).getMsgText());
        Log.d("chatActivity listMessages: ", listOfMessages.get(2).getSenderUserName() + listOfMessages.get(2).getMsgTime() + listOfMessages.get(2).getMsgText());






        int[] images = {R.drawable.ic_smile, R.drawable.ic_kiss, R.drawable.ic_think,
                R.drawable.ic_wink, R.drawable.ic_expressionless, R.drawable.ic_star};
        GridAdaptor gridAdaptor = new GridAdaptor(this, images);
        GridView gv = findViewById(R.id.grid_sticker);
        gv.setAdapter(gridAdaptor);
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