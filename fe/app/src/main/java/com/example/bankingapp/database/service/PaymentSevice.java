package com.example.bankingapp.database.service;

import com.example.bankingapp.database.models.BillEntity;
import com.example.bankingapp.database.models.PaymentHistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PaymentSevice {
//    @GET("payment/search")
//    Call<List<PaymentHistoryItem>> findAll(@Query("forUser") String forUser);
    @GET("payment/current")
    Call<List<PaymentHistoryItem>> getPaymentByCate(@Query("category") String category, @Header("Authorization") String token);
    @GET("bill/{code}")
    Call<BillEntity> getBill(@Path("code") String code, @Header("Authorization") String token);

    @POST("bill/{code}/pay")
    Call<Object> payBill(@Path("code") String code, @Header("Authorization") String token);


}
