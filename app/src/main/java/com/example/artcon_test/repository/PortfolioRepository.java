package com.example.artcon_test.repository;

import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortfolioRepository {
    private final UserService userService;

    public PortfolioRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }

    public void getPortfolio(String userId, PortfolioCallback callback) {
        Call<List<PortfolioPost>> call = userService.getUserPortfolio(userId);

        call.enqueue(new Callback<List<PortfolioPost>>() {
            @Override
            public void onResponse(Call<List<PortfolioPost>> call, Response<List<PortfolioPost>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error fetching posts data");
                }
            }

            @Override
            public void onFailure(Call<List<PortfolioPost>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public interface PortfolioCallback {
        void onSuccess(List<PortfolioPost> posts);

        void onError(String errorMessage);
    }



}
