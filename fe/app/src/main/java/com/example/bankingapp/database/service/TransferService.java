package com.example.bankingapp.database.service;

import com.example.bankingapp.database.dto.TransitionDTO;
import com.example.bankingapp.database.dto.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TransferService {

    @POST("user/tranfer")
    Call<Object> transfer(@Header("Authorization") String token, @Body TransitionDTO transitionDTO);
}
