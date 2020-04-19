package com.example.learnquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.learnquest.Back.User;
import com.example.learnquest.Fragments.DashboardFragment;
import com.example.learnquest.Fragments.FightFragment;
import com.example.learnquest.Fragments.ShopFragment;
import com.example.learnquest.Fragments.TrainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    TextView test;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        test = findViewById(R.id.test);
        Intent intent = getIntent();
        test.setText(intent.getStringExtra("id"));


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


}
