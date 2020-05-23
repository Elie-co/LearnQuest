package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.learnquest.Back.Partie;
import com.example.learnquest.Back.User;
import com.example.learnquest.Fragments.DashboardFragment;
import com.example.learnquest.Fragments.FightFragment;
import com.example.learnquest.Fragments.HistoFragment;
import com.example.learnquest.Fragments.ShopFragment;
import com.example.learnquest.Fragments.TrainFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    String id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_dashboard:
                        selectedFragment = new DashboardFragment();
                        break;
                    case R.id.navigation_fight:
                        selectedFragment = new FightFragment();
                        break;
                    case R.id.navigation_train:
                        selectedFragment = new TrainFragment();

                        break;
                    case R.id.navigation_shop:
                        selectedFragment = new ShopFragment();
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.mfl, selectedFragment).commit();

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.mfl, new DashboardFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> s = new LineGraphSeries<>(new DataPoint[] {new DataPoint(0,-5), new DataPoint(2,5)});
        graph.addSeries(s);
    }

    public void cardpressed(View view) {
        Intent intent = new Intent(this , TrainActivity.class);
        intent.putExtra("type","Orthographe");
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void histo(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mfl,new HistoFragment()).commit();

        db.collection("user").document(id).collection("partie").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    addhisto(documentSnapshot.getId());
                }
            }
        });
    }

    private void addhisto(String id) {
        final TextView txt = findViewById(R.id.histoTextView);
        txt.append("\n" + id );
    }
}
