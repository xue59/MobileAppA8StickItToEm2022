package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class loginSuccessActivity extends AppCompatActivity {
    RecyclerView usersRecycleView;
    List<User> listOfUsers ;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here 开始这里coding
        listOfUsers = new ArrayList<>();
//        listOfUsers.add(new User("userID123", "userNameZack"));
//        listOfUsers.add(new User("userID456", "userNameVictor"));
//        listOfUsers.add(new User("userID789", "userNameMike"));

        usersRecycleView = findViewById(R.id.all_users_recycle_view);
        usersRecycleView.setHasFixedSize(true);
        usersRecycleView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseApp.initializeApp(this);
        myRef = FirebaseDatabase.getInstance().getReference(); // Get the root reference of the database
        myRef.child("users");
        myRef.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.v("Changed Child", "onChildChanged: " + snapshot);
                        Log.v("Changed Child", "onChildChanged: " + snapshot.getKey().toString());
                        listOfUsers.add(new User(snapshot.getValue().toString(), snapshot.getValue().toString()));
                        usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, loginSuccessActivity.this));
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.v("TAG", "onChildChanged: " + snapshot.getValue().toString());
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