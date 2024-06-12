package com.example.bankingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bankingapp.R;
import com.example.bankingapp.fragment.Home;
import com.example.bankingapp.fragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");

        if (fragment == null || fragment.equals("home")) {
            loadFragment(new Home(), false);
        } else if (fragment.equals("setting")) {
            loadFragment(new Home(), true);
            loadFragment(new Setting(), true);
        }




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((item) -> {
            if (item.getItemId() == R.id.home_item) {
                loadFragment(new Home(), true);
            } else if (item.getItemId() == R.id.setting) {
                loadFragment(new Setting(), true);
            }
            return true;
        });
    }




    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.getTag());

        // Add the Fragment to the layout container
        fragmentTransaction.replace(R.id.main_fragment, fragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
    }
}
