package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class loginSuccessActivity extends AppCompatActivity {
    RecyclerView usersRecycleView;
    List<User> listOfUsers ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar

        //staring code here 开始这里coding
        listOfUsers = new ArrayList<>();
        listOfUsers.add(new User("userID123", "userNameZack"));
        listOfUsers.add(new User("userID456", "userNameVictor"));
        listOfUsers.add(new User("userID789", "userNameMike"));

        usersRecycleView = findViewById(R.id.all_users_recycle_view);
        usersRecycleView.setHasFixedSize(true);
        usersRecycleView.setLayoutManager(new LinearLayoutManager(this));
        usersRecycleView.setAdapter(new AUserAdapter(listOfUsers, this));


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