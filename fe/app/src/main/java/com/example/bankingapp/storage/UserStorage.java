package com.example.bankingapp.storage;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.bankingapp.database.models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UserStorage {

    private static final String PREFERENCES_FILE = "encrypted_prefs";
    private static final String USER_KEY = "user";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public UserStorage(Context context) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFERENCES_FILE,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        gson = new Gson();
    }

    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        sharedPreferences.edit().putString(USER_KEY, userJson).apply();
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        return userJson != null ? gson.fromJson(userJson, User.class) : null;
    }

    public void clearUser() {
        sharedPreferences.edit().remove(USER_KEY).apply();
    }
}

