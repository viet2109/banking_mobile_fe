package com.example.bankingapp.database.service;


import com.example.bankingapp.database.dto.Response;
import com.example.bankingapp.database.dto.UserDTO;
import com.example.bankingapp.database.models.PaymentHistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthService {

    @POST("auth/login")
    Call<Response.LogIn> login(@Body UserDTO.LogIn user);

    @POST("auth/register")
    Call<UserDTO.Register> register(@Body UserDTO.Register user);

    @POST("currency/exchange")
    Call<Integer> exchangeCurrency(@Query("fromCurrency") String fromCurrency,
                                   @Query("toCurrency") String toCurrency,
                                   @Query("amount") int amount);
    @GET("payment/search")
    Call<List<PaymentHistoryItem>> findAll(@Query("forUser") String forUser);


}
