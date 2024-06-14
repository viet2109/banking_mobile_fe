package com.example.bankingapp.database.service;

import com.example.bankingapp.database.dto.UserDTO;
import com.example.bankingapp.database.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("user")
    Call<List<User>> getUsers();

//    @GET("/user/{email}")
//    Call<User> getUserByEmail(@Path("email") String email);

    @GET("user/{cardnumber}")
    Call<UserDTO> getUserCardNumber(@Header("Authorization") String token, @Path("cardnumber") String card);

    @POST("login")
    Call<User> login(@Body User user);
}
