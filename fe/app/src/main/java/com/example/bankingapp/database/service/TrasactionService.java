package com.example.bankingapp.database.service;

import com.example.bankingapp.database.models.TransactionHistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TrasactionService {
    @GET("transition/current")
    Call<List<TransactionHistoryItem>> getTransitonHistoryList(@Header("Authorization") String token);
}
