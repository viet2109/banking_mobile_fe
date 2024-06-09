package com.example.bankingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.LanguageAdapter;
import com.example.bankingapp.storage.UserStorage;
import com.example.bankingapp.utils.LanguageItem;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Language extends BaseActivity implements LanguageAdapter.OnLanguageSelectedListener {

    private RecyclerView recyclerView;
    private LanguageAdapter adapter;
    private List<LanguageItem> languageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setupBackButton();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        languageList = new ArrayList<>();
        languageList.add(new LanguageItem("English", R.drawable.en, "en"));
        languageList.add(new LanguageItem("Việt Nam", R.drawable.vn, "vi"));
        // Add other languages...

        // Lấy mã ngôn ngữ hiện tại từ SharedPreferences
        UserStorage userStorage = null;
        try {
            userStorage = new UserStorage(this);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        String currentLangCode = userStorage.getLanguage();

        adapter = new LanguageAdapter(this, languageList, currentLangCode, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLanguageSelected(LanguageItem languageItem) throws GeneralSecurityException, IOException {
        setLocale(this, languageItem.getLocaleCode());
    }

    public void setLocale(Context context, String langCode) throws GeneralSecurityException, IOException {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());

        config.setLocale(locale);
        context.createConfigurationContext(config);

        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);

        // Lưu lại ngôn ngữ vào SharedPreferences
        UserStorage userStorage = new UserStorage(this);
        userStorage.saveLanguage(langCode);

        recreate();
    }

    @Override
    public void setupBackButton() {
        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment", "setting");
            startActivity(intent);
        });
    }
}
