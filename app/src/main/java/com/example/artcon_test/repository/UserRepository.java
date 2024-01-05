package com.example.artcon_test.repository;

import android.util.Log;

import com.example.artcon_test.model.MediaFile;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;
import com.example.artcon_test.network.ApiConfig;
import com.example.artcon_test.network.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    String TAG = "hatsunemiku";
    private final UserService userService;

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }

    public void getUserById(String userId, UserCallback callback) {

        Call<User> call = userService.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error fetching user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });

    }

    public void getHome(String userId,PostRepository.PostCallback callback) {
        Call<List<Post>> call = userService.getHomeFeed(userId);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();

                    Log.d(TAG, "received posts:" + response.body());
                    for (Post post : posts) {
                        List<MediaFile> mediaFiles = post.getMediaFiles();
                        if (mediaFiles != null && !mediaFiles.isEmpty()) {
                            Log.d(TAG, "MediaFiles for post " + post.getId() + ": " + mediaFiles);
                        } else {
                            Log.d(TAG, "No MediaFiles for post " + post.getId());
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

    // Callback interface for handling asynchronous responses
    public interface UserCallback {
        void onSuccess(User user);

        void onError(String errorMessage);
    }
}
