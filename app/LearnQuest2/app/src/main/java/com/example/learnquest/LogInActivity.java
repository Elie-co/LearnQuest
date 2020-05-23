package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText limet;
    private EditText lipet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_activity);
        limet = findViewById(R.id.limet);
        lipet = findViewById(R.id.lipet);
    }


    public void livb_pressed(View view) {
        final String mail = limet.getText().toString();
        String password = lipet.getText().toString();

        if(mail.isEmpty() || password.isEmpty()){
            Log.d("Dev","One Edit Text is Empty");
            //TODO : Un field est vide
        }else{
            mAuth.signInWithEmailAndPassword(mail,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        goMain();
                        } else {
                        Log.d("Dev","Wrong Password or E-mail");
                        //TODO : Mdp ou e-mail match pas avec un compte
                    }
                }
            })
        ;}
    }

    private void goMain(){
        Intent intent = new Intent(this,MainActivity.class);
        if(mAuth.getCurrentUser() != null){
            intent.putExtra("id",mAuth.getCurrentUser().getUid());
        }
        startActivity(intent);
    }

}
