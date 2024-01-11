package com.example.artcon_test.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;

import java.util.List;

public interface PostService {

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

    @GET("/post/{postId}")
    Call<Post> getPostById(@Path("postId") String postId);
}