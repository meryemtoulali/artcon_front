package com.example.artcon_test.network;

import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @GET("user/{userId}")
    Call<User> getUserById(@Path("userId") String userId);
    @GET("user/{userId}/portfolio")
    Call<List<PortfolioPost>> getUserPortfolio(@Path("userId") String userId);

    @GET("post/owner/{userId}")
    Call<List<Post>> getUserPostList(@Path("userId") String userId);

    @Multipart
    @PUT("user/{userId}")
    Call<Void> setupProfile(
            @Path("userId") String userId,
            @Part MultipartBody.Part picture,
            @Part("title") RequestBody title,
            @Part("type") RequestBody type
    );

    @PUT("user/update-interest/{userId}")
    Call<Void> selectInterests(
            @Path("userId") String userId,
            @Body List<Long> interests
    );
}