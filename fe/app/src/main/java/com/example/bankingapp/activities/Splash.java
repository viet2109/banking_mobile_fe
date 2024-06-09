package com.example.bankingapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.example.bankingapp.R;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;

public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            loadLocale();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        }, 3000);
    }

    private void loadLocale() throws GeneralSecurityException, IOException {
        UserStorage userStorage = new UserStorage(this);
        String currentLangCode = userStorage.getLanguage();
        setLocale(currentLangCode);
    }

    public void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = new Configuration(resources.getConfiguration());

        config.setLocale(locale);
        this.createConfigurationContext(config);

        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);


    }
}