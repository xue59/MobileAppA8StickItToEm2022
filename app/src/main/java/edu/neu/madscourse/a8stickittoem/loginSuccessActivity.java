package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Externalizable;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class loginSuccessActivity extends AppCompatActivity{
    RecyclerView usersRecycleView;
    List<User> listOfUsers ;
    private DatabaseReference myRef;
    public User logined_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here 开始这里coding
        logined_user = (User) getIntent().getSerializableExtra("logined_user");
        Log.d("logined_user", "" + logined_user.getUserID() + logined_user.getUsername());
        Toast.makeText(getApplicationContext(), "Welcome " + logined_user.getUsername() + "(" + logined_user.getUserID() + ")", Toast.LENGTH_LONG)
                .show();
        listOfUsers = new ArrayList<>();

//        listOfUsers.add(new User("userID123", "userNameZack"));
//        listOfUsers.add(new User("userID456", "userNameVictor"));
//        listOfUsers.add(new User("userID789", "userNameMike"));

        usersRecycleView = findViewById(R.id.all_users_recycle_view);
        usersRecycleView.setHasFixedSize(true);
        usersRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, loginSuccessActivity.this));

        FirebaseApp.initializeApp(this);
        myRef = FirebaseDatabase.getInstance().getReference(); // Get the root reference of the database
        myRef.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String userID= snapshot.getValue().toString();
                        String userName= snapshot.getKey().toString();
                        Log.v("Changed Child",  userID + " get value: " + logined_user.getUserID());
//                        Log.v("Changed Child", "get key: " + snapshot.getKey().toString());
                        // 数据库内： Key是userName， value是UserID
                        if(!userID.equals(logined_user.getUserID())) {
                            listOfUsers.add(new User(userID, userName));
                            usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, loginSuccessActivity.this, logined_user));
                            Log.d("onChildAdd", ""+listOfUsers);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        listOfUsers.clear();
                        listOfUsers.add(new User(snapshot.getValue().toString(), snapshot.getKey().toString()));
                        if(snapshot.getValue().toString() == logined_user.getUserID()) {
                            listOfUsers.add(new User(snapshot.getValue().toString(), snapshot.getKey().toString()));
                            Log.d("onChildChange", ""+listOfUsers);
                        }
                        usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, loginSuccessActivity.this, logined_user));
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        listOfUsers.remove(new User(snapshot.getValue().toString(), snapshot.getKey().toString()));
                        if(snapshot.getValue().toString() != logined_user.getUserID()) {
                            listOfUsers.add(new User(snapshot.getValue().toString(), snapshot.getKey().toString()));
                        }
                        usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, loginSuccessActivity.this, logined_user));

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        //用于展示 recycle view



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