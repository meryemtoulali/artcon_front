package com.example.artcon_test.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.model.Interest;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.artcon_test.model.Comment;
import com.example.artcon_test.model.CommentRequest;

import com.example.artcon_test.model.LikeRequest;
import com.example.artcon_test.model.LikeRes;
import com.example.artcon_test.model.Post;

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

    @GET("/hasUserLikedPost")
    Call<Boolean> hasUserLikedPost(
            @Query("userId") Integer userId,
            @Query("postId") Integer postId
    );

    @POST("/like")
    Call<LikeRes> likePost (
            @Body LikeRequest request
    );

    @POST("/dislike")
    Call<LikeRes> dislikePost (
            @Body LikeRequest request
    );

    @GET("/post/{postId}/likes")
    Call<Integer> getLikeCount (
            @Path("postId") String postId
    );

    @GET("/comment/byPost/{postId}")
    Call<List<Comment>> getCommentsByPostId(
            @Path("postId") String postId
    );

    @POST("/comment")
    Call<Comment> comment(
            @Body CommentRequest request
    );

    @GET("post/search")
    Call<List<Post>> searchPostsIgnoreCase(@Query("query") String query);
}