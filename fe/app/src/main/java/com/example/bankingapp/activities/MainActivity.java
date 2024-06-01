package com.example.bankingapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bankingapp.R;
import com.example.bankingapp.fragment.Home;
import com.example.bankingapp.fragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new Home());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((item) -> {
           if (item.getItemId() == R.id.home_item) {
               loadFragment(new Home());
           } else if (item.getItemId() == R.id.setting) {
               loadFragment(new Setting());
           }
            return true;
        });

    }

    private void loadFragment(Fragment fragment) {
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the Fragment to the layout container
        fragmentTransaction.replace(R.id.main_fragment, fragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
    }
}