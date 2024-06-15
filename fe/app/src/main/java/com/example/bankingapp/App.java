package com.example.bankingapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            UserStorage userStorage = new UserStorage(this);
            userStorage.clearUser();
            userStorage.clearToken();
            userStorage.clearLanguage();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
