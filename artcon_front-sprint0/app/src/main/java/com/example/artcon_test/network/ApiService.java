package com.example.artcon_test.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;

public interface ApiService {
    @POST("login")
    Single<LoginResponse> login(@Body LoginRequest request);
}

