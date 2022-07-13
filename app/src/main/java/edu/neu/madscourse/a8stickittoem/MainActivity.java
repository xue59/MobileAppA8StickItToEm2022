package edu.neu.madscourse.a8stickittoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText etLogin;
    private DatabaseReference dataRef;
    private ProgressBar loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = findViewById(R.id.editTxt_login);
        loadingCircle = findViewById(R.id.loadingCircle);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String name = etLogin.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            loadingCircle.setVisibility(View.VISIBLE);
            dataRef = FirebaseDatabase.getInstance().getReference().child("User").child(name);
            dataRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                        UserModel userModel = task.getResult().getValue(UserModel.class);
                        if (userModel != null&&!TextUtils.isEmpty(userModel.name)) {
                            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                            sp.edit().putString("name",name).apply();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }

                    }
                    loadingCircle.setVisibility(View.GONE);
                }
            });

        }
    }
}