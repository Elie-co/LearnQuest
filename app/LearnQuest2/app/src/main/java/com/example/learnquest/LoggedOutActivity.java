package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedOutActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        mAuth.signOut();
        super.onStart();
        setContentView(R.layout.loggedout_activity);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            alreadyConnected();
        }
    }

    private void alreadyConnected(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id",mAuth.getCurrentUser().getUid());
        startActivity(intent);
    }

    public void lolib_pressed(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void lorb_pressed(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
