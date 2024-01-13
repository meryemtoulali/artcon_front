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
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/{userId}")
    Call<User> getUserById(@Path("userId") String userId);
    @GET("user/search")
    Call<List<User>> searchPeopleIgnoreCase(@Query("query") String query);
    @GET("user/{userId}/portfolio")
    Call<List<PortfolioPost>> getUserPortfolio(@Path("userId") String userId);
    @POST("user/logout")
    Call<Void> logout();

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

    @GET("user/{userId}/home")
    Call<List<Post>> getHomeFeed(
            @Path("userId") String userId
    );

    @GET("followers/check/{followerId}/{followingId}")
    Call<Boolean> checkFollows(
            @Path("followerId") String followerId,
            @Path("followingId") String followingId
    );


    @POST("follow/{followerId}/{followingId}")
    Call<Void> followUser(
            @Path("followerId") String followerId,
            @Path("followingId") String followingId
    );
    @POST("unfollow/{followerId}/{followingId}")
    Call<Void> unfollowUser(
            @Path("followerId") String followerId,
            @Path("followingId") String followingId
    );


}