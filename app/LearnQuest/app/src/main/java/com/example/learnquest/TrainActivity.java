package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.Back.OneGap;
import com.example.learnquest.Back.Partie;
import com.example.learnquest.Back.Perso;
import com.example.learnquest.Back.Questions;
import com.example.learnquest.Back.Skin;
import com.example.learnquest.Back.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class TrainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    String type;
    User player;
    Partie p;
    OneGap currentQuestion;
    HashSet<OneGap> qts = new HashSet<>();
    ArrayList<Questions> questions = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        Log.d("moi","ONCREATE");
    }

    @Override
    protected void onStart() {
        Log.d("moi","ONSTART");

        super.onStart();
        DocumentReference userRef = db.collection("user").document(id);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    player = documentSnapshot.toObject(User.class);
                    Log.d("moi","OK");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Skin skin = new Skin(0,"test","test");
                Perso perso = new Perso(150,10,10,skin);
                player = new User(0,0,perso,0,"Username");
                Log.d("moi","NOK");
            }
        });
    }

    public void rungame(View view) {
        Perso user = this.player.getPerso();
        Skin skin = new Skin(0,"hero1","hero2");
        Perso adversaire = new Perso(200,0,20,skin);
        p = new Partie(user, adversaire,questions);
        final HashSet<OneGap> possibleQuestions = new HashSet<>();
        CollectionReference questions = db.collection("grammary").document("Fillgap_1_0_kb").collection("exercises");
        questions.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("moi","SuCCES");
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(qts.size() < 1){
                        OneGap oneGap = documentSnapshot.toObject(OneGap.class);
                        qts.add(oneGap);
                        Log.d("moi","ADD A QUESTION");
                    }

                }
                runQuestion();
            }
        });

    }

    private void runQuestion() {
        if(p.isEnd()){
            goResult();
        } else {
            setContentView(R.layout.fill_the_gap_layout);
            OneGap question = null;
            if(!qts.isEmpty()) {
                Iterator<OneGap> i = qts.iterator();
                while (i.hasNext()) {
                    if (question == null) {
                        question = i.next();
                    }
                }
            }
            if(!qts.isEmpty()){
            TextView ftgFrench = findViewById(R.id.ftgfr);
            ftgFrench.setText(question.getFrench());
            TextView ftgQuestion = findViewById(R.id.ftgquestion);
            int answerSize = question.getAnswer().length();
            String middle = "";
            while (answerSize !=0){middle += '_';answerSize--;}
            ftgQuestion.setText(question.getBefore()+middle+question.getAfter());
            currentQuestion = question;
            }
        }
    }

    private void goResult() {
        db.collection("user").document(id).collection("partie").add(p);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void answer(View view) {
        Perso adversaire = p.getAdversaire();
        Perso user = p.getUser();
        EditText answerEditText = findViewById(R.id.ftganswer);
        int damage;
        if (currentQuestion.getAnswer().equals(answerEditText.getText().toString())){
            currentQuestion.setGoodAnswer(true);
            if(adversaire.getDef() >= user.getAtk()){
                damage = 1;
            }else{
                damage = user.getAtk() - adversaire.getDef();
            }
            adversaire.setHp(adversaire.getHp()-damage);
        }else{
            currentQuestion.setGoodAnswer(false);
            if(user.getDef() >= adversaire.getAtk()){
                damage = 1;
            }else{
                damage = adversaire.getAtk() - user.getDef();
            }
            user.setHp(user.getHp()-damage);
        }
        questions.add(currentQuestion);
        Log.d("moi",questions.toString());
        this.p = new Partie(user, adversaire, questions);
        runQuestion();
    }
}
