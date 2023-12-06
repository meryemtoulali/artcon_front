package com.example.artcon_test.network;

import com.example.artcon_test.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("user/find/{userId}")
    Call<User> getUserById(@Path("userId") String userId);
}