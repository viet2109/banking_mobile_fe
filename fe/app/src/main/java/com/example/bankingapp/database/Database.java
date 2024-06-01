package com.example.bankingapp.database;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Database {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.9:8081/api/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
