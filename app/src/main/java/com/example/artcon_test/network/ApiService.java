package com.example.artcon_test.network;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.model.Category;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;

import java.util.List;
import java.util.Optional;

public interface ApiService {

    @GET("/interest/all")
    Call<List<Interest>> getAllInterests();


    @Multipart
    @POST("/post/add")
    Call<AddPostRes> submitPost(
            @Part("user_id") Integer userId,
            @Part("description") String description,
            @Part List<MultipartBody.Part> mediafiles,
            @Part("interest_id") Long interestId
    );
}