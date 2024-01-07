package com.example.artcon_test.network;

import com.example.artcon_test.model.Interest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterestService {
    @GET("interest/all")
    Call<List<Interest>> getInterests();
}
