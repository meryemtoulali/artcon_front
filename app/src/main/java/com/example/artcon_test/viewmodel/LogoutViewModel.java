package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogoutViewModel extends ViewModel {
    private static final String BASE_URL = ApiConfig.BASE_URL;
    private static Retrofit retrofit;
    private final UserService userService;

    public LogoutViewModel(){
        Log.d("logout","Retrofit");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        userService = retrofit.create(UserService.class);
    }
    public void logout() {
        Call<Void> call = userService.logout();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("logout", "Logout successful");
                } else {
                    Log.e("logout", "Logout failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("logout", "Network error", t);
            }
        });
    }
}
