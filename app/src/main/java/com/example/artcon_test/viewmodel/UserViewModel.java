package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.ArtistTypeService;
import com.example.artcon_test.network.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {
    private static final String BASE_URL = ApiConfig.BASE_URL;
    private static Retrofit retrofit;
    public static UserService updateUserService(){
        Log.d("User","Update user");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(UserService.class);
    }
}
