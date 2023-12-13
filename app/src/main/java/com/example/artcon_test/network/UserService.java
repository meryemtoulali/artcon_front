package com.example.artcon_test.network;

import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("user/{userId}")
    Call<User> getUserById(@Path("userId") String userId);
    @GET("user/{userId}/portfolio")
    Call<List<PortfolioPost>> getUserPortfolio(@Path("userId") String userId);
}