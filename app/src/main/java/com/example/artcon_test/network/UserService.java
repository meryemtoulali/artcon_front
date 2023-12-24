package com.example.artcon_test.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

//    @PUT("user/{id}")
//    Call<Void> updateUser(@Path("id") String id, @Body UpdateUserRequest data);

    @Multipart
    @PUT("user/{userId}")
    Call<Void> setupProfile(
            @Path("userId") int userId,
            @Part MultipartBody.Part picture,
            @Part("title") RequestBody title,
            @Part("type") RequestBody type
    );

    @PUT("user/update-interest/{userId}")
    Call<Void> selectInterests(
            @Path("userId") int userId,
            @Body List<Long> interests
    );
}
