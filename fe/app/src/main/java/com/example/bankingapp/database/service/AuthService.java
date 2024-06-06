package com.example.bankingapp.database.service;

import com.example.bankingapp.database.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthService {

    @POST("auth/login")
    Call<User> login(@Body User user);

    @POST("auth/register")
    Call<User> register(@Body User user);

    @POST("currency/exchange")
    Call<Integer> exchangeCurrency(@Query("fromCurrency") String fromCurrency,
                                   @Query("toCurrency") String toCurrency,
                                   @Query("amount") int amount);



}
