package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.artcon_test.network.ArtistTypeService;
import com.example.artcon_test.network.InterestService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterestViewModel extends ViewModel {
    private static final String BASE_URL = "http://100.89.26.137:8080/";
    private static Retrofit retrofit;

    public static InterestService getInterests(){
        Log.d("Interests","Get interest");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(InterestService.class);
    }
}
