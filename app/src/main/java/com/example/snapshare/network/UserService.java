package com.example.snapshare.network;

import com.example.snapshare.models.LoginRequest;
import com.example.snapshare.models.LoginResponse;
import com.example.snapshare.models.RegisterResponse;
import com.example.snapshare.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/users/register")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("/users/login")
    Call<LoginResponse> loginUser(@Body User user);

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") String userId);

}

