package com.example.artcon_test.repository;

import android.util.Log;

import com.example.artcon_test.model.MediaItem;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.PostService;
import com.example.artcon_test.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {
    private final UserService userService;
    PostService postService;
    String TAG ="hatsunemiku";

    public PostRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        postService = retrofit.create(PostService.class);
    }

    public void getPostList(String userId, PostCallback callback) {
        Call<List<Post>> call = userService.getUserPostList(userId);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    if (posts != null) {
                        Log.d(TAG, "received posts:" + response.body());
                        for (Post post : posts) {
                            List<MediaItem> mediaFiles = post.getMediaFiles();
                        }
                    }

                    callback.onSuccess(posts);

                } else {
                    callback.onError("Error fetching posts data");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public interface PostCallback {
        void onSuccess(List<Post> postList);

        void onError(String errorMessage);
    }

    public Call<List<Post>> searchPosts(String query) {
        return postService.searchPostsIgnoreCase(query);
    }
}
