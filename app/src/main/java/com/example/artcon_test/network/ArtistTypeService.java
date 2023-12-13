package com.example.artcon_test.network;

import com.example.artcon_test.model.ArtistType;
import com.example.artcon_test.model.Interest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArtistTypeService {
    @GET("artistType/all")
    Call<List<ArtistType>> getArtistType();

    @GET("interest/all")
    Call<List<Interest>> getInterests();

}
