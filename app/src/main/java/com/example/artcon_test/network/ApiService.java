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
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;

public interface ApiService {
    @POST("login")
    Single<LoginResponse> login(@Body LoginRequest request);

    @Multipart
    @POST("/post/add")
    Call<AddPostRes> addpost(@Part("post_owner") Integer user_id,
                             //get current username from Shared Preferences
                             @Part("post_description") RequestBody descriptionText,
                             @Part MultipartBody.Part image,
                             @Part("post_category") RequestBody category);

   // @GET("getCategoryId")
    //Call<Category> getCategoryId(@Query("categoryName") String categoryName);

}

