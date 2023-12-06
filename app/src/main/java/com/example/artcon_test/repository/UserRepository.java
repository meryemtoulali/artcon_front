package com.example.artcon_test.repository;

import android.util.Log;

import com.example.artcon_test.model.User;
import com.example.artcon_test.network.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private final UserService userService;

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }

    public void getUserById(String userId, UserCallback callback) {

        Call<User> call = userService.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error fetching user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });

    }

    // Callback interface for handling asynchronous responses
    public interface UserCallback {
        void onSuccess(User user);

        void onError(String errorMessage);
    }

}
