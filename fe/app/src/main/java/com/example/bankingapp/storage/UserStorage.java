package com.example.bankingapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.bankingapp.database.dto.UserDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UserStorage {

    private static final String PREFERENCES_FILE = "prefs";
    private static final String USER_KEY = "user";
    private static final String TOKEN_KEY = "token";
    private static final String LANGUAGE_KEY = "lang";

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

    public void saveUser(UserDTO user) {
        String userJson = gson.toJson(user);
        sharedPreferences.edit().putString(USER_KEY, userJson).apply();
    }

    public UserDTO getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        return userJson != null ? gson.fromJson(userJson, UserDTO.class) : null;
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void saveLanguage(String language) {
        sharedPreferences.edit().putString(LANGUAGE_KEY, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE_KEY, "en");
    }

    public void clearUser() {
        sharedPreferences.edit().remove(USER_KEY).apply();
    }

    public void clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply();
    }
}
