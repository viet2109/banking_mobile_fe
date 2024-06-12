package com.example.bankingapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class AppStorage {

    private static final String PREF_NAME = "data";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public AppStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Add other methods for different data types as needed
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }
}

