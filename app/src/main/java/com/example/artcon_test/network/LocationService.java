package com.example.artcon_test.network;

import com.example.artcon_test.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationService {
    @GET("location/all")
    Call<List<Location>> getLocations();
}
