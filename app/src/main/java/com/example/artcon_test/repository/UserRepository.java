package com.example.artcon_test.repository;

import com.example.artcon_test.model.User;
import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    String TAG = "hatsunemiku";
    private final UserService userService;

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
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

    public Call<List<User>> searchPeople(String query) {
        return userService.searchPeopleIgnoreCase(query);
    }

    // Callback interface for handling asynchronous responses
    public interface UserCallback {
        void onSuccess(User user);

        void onError(String errorMessage);
    }







}
