package com.example.artcon_test.network;

import com.example.artcon_test.model.LikeRequest;
import com.example.artcon_test.model.LikeRes;
import com.example.artcon_test.model.PortfolioPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PortfolioPostService {

    @GET("/hasUserLikedPortfolioPost")
    Call<Boolean> hasUserLikedPortfolioPost(
            @Query("userId") Integer userId,
            @Query("postId") Integer postId
    );

    @POST("/portfolioPostLike")
    Call<LikeRes> portfolioPostLike (
            @Body LikeRequest request
    );

    @POST("/portfolioPostDislike")
    Call<LikeRes> portfolioPostDislike (
            @Body LikeRequest request
    );

    @GET("/portfolio/{portfolioPostId}")
    Call<PortfolioPost> getPortfolio (
            @Path("postId") String postId
    );

}
