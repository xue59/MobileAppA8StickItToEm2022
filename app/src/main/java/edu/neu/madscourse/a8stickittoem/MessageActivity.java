package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {
    private FirebaseListAdapter<Message> adapter;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new Message(input.getText().toString(), user)
                        );

                // Clear the input
                input.setText("");
            }
        });

    }

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
}