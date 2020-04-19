package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.Back.Perso;
import com.example.learnquest.Back.Skin;
import com.example.learnquest.Back.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firestore.v1beta1.WriteResult;

import org.w3c.dom.Document;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText rmet;
    private EditText rp1et;
    private EditText rp2et;
    private EditText ruet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.register_activity);
        rmet = findViewById(R.id.rmet);
        rp1et = findViewById(R.id.rp1et);
        rp2et = findViewById(R.id.rp2et);
        ruet = findViewById(R.id.ruet);
    }


    public void rvb_pressed(View view) {
        String mail = rmet.getText().toString();
        String password = rp1et.getText().toString();
        String passwordValidation = rp2et.getText().toString();
        String username = ruet.getText().toString();
        if(mail.isEmpty() || password.isEmpty() || passwordValidation.isEmpty() || username.isEmpty()){
            Log.d("Dev","One fiels is empty");
            //TODO : Un field est vide
        } else if(password.length() < 8) {
            Log.d("Dev","Le Mot de passe est trop cours (minimium 8)");
            //TODO : Le MDP est trop court
        }else if(!(password.equals(passwordValidation))){
            Log.d("Dev","Les mots de passes sont différents");
            //TODO : Les MDP sont différents
        } else {
            mAuth.createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        createUser();
                        goMain();
                    } else {
                        Log.d("Dev","Le compte existe déjà");
                        //TODO : Le compte existe déjà
                    }
                }
            })
            ;
        }
    }

    private void createUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        DocumentReference newUser = db.collection("user").document(mAuth.getCurrentUser().getUid());
        DocumentReference newSkin = newUser.collection("skins").document("hero");
        Skin skin = new Skin();
        skin.setPrice(0);
        skin.setBase1("hero1");
        skin.setBase2("hero2");
        Perso perso = new Perso();
        perso.setAtk(20);
        perso.setDef(10);
        perso.setHp(100);
        perso.setSkin(skin);
        User user = new User();
        user.setCoin(0);
        user.setExperience(0);
        user.setPerso(perso);
        user.setTheta(0);
        user.setUsername(ruet.getText().toString());
        newUser.set(user);
        newSkin.set(skin);
    }

    private void goMain(){
        Intent intent = new Intent(this,MainActivity.class);
        if(mAuth.getCurrentUser() != null){
            intent.putExtra("id",mAuth.getCurrentUser().getUid());
        }
        startActivity(intent);
    }
}
