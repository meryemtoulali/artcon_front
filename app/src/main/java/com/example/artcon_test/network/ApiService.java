
package com.example.artcon_test.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.example.artcon_test.model.ArtistType;
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;

import java.util.List;

public interface ApiService {
    @POST("login")
    Single<LoginResponse> login(@Body LoginRequest request);

//    @PUT("your-endpoint/{id}")
//    Call<Void> updateData(@Path("id") String id, @Body YourDataModel data);

}
