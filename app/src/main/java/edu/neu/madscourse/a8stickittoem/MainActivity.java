package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Externalizable;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    public EditText editTxt_login;
    private DatabaseReference myRef;
    User logined_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // starting code here:
        editTxt_login = (EditText) findViewById(R.id.editTxt_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openLoginSuccessActivity();}
        });




    }

    public void openLoginSuccessActivity(){
        Log.d("openLoginSuccessActivity", "checking user name...." + editTxt_login.getText().toString());
        FirebaseApp.initializeApp(this);
        myRef = FirebaseDatabase.getInstance().getReference(); // Get the root reference of the database
        String inputString = editTxt_login.getText().toString();
        if (inputString.length() < 1){
            inputString = "ErrorUsernameForEver"; //如果input login小于1， 设置为永远错误的user name 导致无法登陆
        }
        myRef.child("users").child(inputString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // user exists in the firebase data base (Key 为 user name)
                    String userName = snapshot.getKey(); // The correct Login in User Name
                    String userID = snapshot.getValue().toString(); // The correct Login in User ID
                    Log.d("True user", "" + userName + " " + userID);
                    logined_user = new User(userID, userName);  // 先放入 User ID； 再放入User Name
                    Intent intent = new Intent(MainActivity.this, loginSuccessActivity.class);
                    intent.putExtra("logined_user", (Serializable) logined_user);
                    startActivity(intent);
                }else{
                    // user does not exist
                    Log.d("No user error", editTxt_login.getText().toString() + " -user does not exist");
                    Toast.makeText(getApplicationContext(), "Incorrect Username! (TA login: Mike)", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        Log.d("check if user name correct",myRef.child("users").child(editTxt_login.getText().toString()).getKey());

//
    }



}