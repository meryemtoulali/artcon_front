package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.network.LocationService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationViewModel extends ViewModel {
    private static final String BASE_URL = "https://artcon-back.onrender.com/";
    private static Retrofit retrofit;
    public static LocationService getLocationService(){
        Log.d("location","this is me trying once again");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(LocationService.class);
    }

}
