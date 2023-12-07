package com.example.artcon_test.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;
import com.example.artcon_test.model.RegisterRequest;

public interface AuthService {
    @POST("user/register")
    Call<LoginResponse> register(@Body RegisterRequest registrationRequest);

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

