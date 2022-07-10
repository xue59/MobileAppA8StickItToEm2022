package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;

public class FireBaseDemo extends AppCompatActivity {

    private DatabaseReference mRootRef;
    private Button loginButton;
    private EditText usernameET;
    private Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootRef = FirebaseDatabase.getInstance().getReference(); // Get the root reference of the database
        loginButton.findViewById(R.id.loginButton);
        usernameET.findViewById(R.id.username_tv);
        sendButton.findViewById(R.id.sendButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = mRootRef.child("users").push().getKey();
                String username = usernameET.toString();
                writeNewUser(userID, username);
            }
        });

        // Monitor messages
        mRootRef.child("messages").addChildEventListener(childEventListener);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emojiID = getEmojiID();
                User sender;
                User receiver;
                LocalDateTime myDT = LocalDateTime.now();
                writeNewMessage(myDT, emojiID, sender, receiver);
            }
        });
    }

    public void writeNewUser(String userId, String name) {
        User user = new User(userId, name);
        mRootRef.child("users").child(userId).setValue(user);
    }

    public void writeNewMessage(LocalDateTime myDT, String emojiID, User sender, User receiver) {
        Message message = new Message(myDT, emojiID, sender, receiver);
        mRootRef.child("messages").child(myDT.toString()).setValue(message);
    }

    public String getEmojiID() {
        return "";
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Message m = snapshot.getValue(Message.class);
            if (m.sender.getUsername().equals(currentUser)) {
                sender.display(m.getEmojiID());
            } else (m.receiver.getUsername().equals(currentUser)) {
                receiver.display(m.getEmojiID());
            }
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
    };
}