package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.ArtistType;
import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.ApiService;
import com.example.artcon_test.network.ArtistTypeService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistTypeViewModel extends ViewModel {

    // Local http://192.168.100.10:8080
    private static final String BASE_URL = ApiConfig.BASE_URL;
    private static Retrofit retrofit;
    public static ArtistTypeService getArtistTypeService(){
        Log.d("ArtistType","Get Artist types");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ArtistTypeService.class);
    }
}
