package com.example.artcon_test.viewmodel;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.artcon_test.network.AuthService;
public class LoginViewModel extends ViewModel {
    private static final String BASE_URL = "http://192.168.1.13:8080/";
    private static Retrofit retrofit;

    public static AuthService getAuthService() {
        Log.d("salma","this is me trying");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(AuthService.class);
    }
}