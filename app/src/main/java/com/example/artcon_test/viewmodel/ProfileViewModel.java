package com.example.artcon_test.viewmodel;

// UserProfileViewModel.java

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.PortfolioRepository;
import com.example.artcon_test.repository.PostRepository;
import com.example.artcon_test.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    String TAG = "hatsunemiku";
    private final UserRepository userRepository = new UserRepository();
    private final PortfolioRepository portfolioRepository = new PortfolioRepository();
    private final PostRepository postRepository = new PostRepository();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<PortfolioPost>> portfolioLiveData = new MutableLiveData<>();

    private MutableLiveData<PortfolioPost> selectedPortfolioPostLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Post>> postListLiveData = new MutableLiveData<>();
    private MutableLiveData<Post> selectedPostLiveData = new MutableLiveData<>();

    private MutableLiveData<List<User>> followersListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> followingListLiveData = new MutableLiveData<>();


    // Method to fetch user data
    public void getUserById(String userId) {
        userRepository.getUserById(userId, new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Update LiveData with the retrieved user data
                userLiveData.postValue(user);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error, maybe update a LiveData for error handling
                Log.e(TAG, errorMessage);
            }
        });
    }

    // Getter for observing the user data
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void isFollowing(String followerId, String followingId, UserRepository.FollowCheckCallback callback) {
        userRepository.checkFollows(followerId, followingId, callback);
    }

    public void followUser(String followerId, String followingId, UserRepository.FollowCallback callback) {
        userRepository.followUser(followerId, followingId, callback);
    }

    public void unfollowUser(String followerId, String followingId, UserRepository.FollowCallback callback) {
        userRepository.unfollowUser(followerId, followingId, callback);
    }



    public void getPortfolio(String userId) {
        portfolioRepository.getPortfolio(userId, new PortfolioRepository.PortfolioCallback() {
            @Override
            public void onSuccess(List<PortfolioPost> posts) {
                portfolioLiveData.postValue(posts);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    public LiveData<List<PortfolioPost>> getPortfolioLiveData() {
        return portfolioLiveData;
    }

    public LiveData<PortfolioPost> getSelectedPortfolioPost() {
        return selectedPortfolioPostLiveData;
    }

    public void setSelectedPortfolioPost(PortfolioPost selectedPost) {
        selectedPortfolioPostLiveData.setValue(selectedPost);
    }

    public void getPostList(String userId) {
        postRepository.getPostList(userId, new PostRepository.PostCallback() {
            @Override
            public void onSuccess(List<Post> postList) {
                postListLiveData.postValue(postList);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    public void getFollowersList(String userId) {
        userRepository.getFollowersList(userId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followersList = response.body();
                    followersListLiveData.postValue(followersList);
                    Log.d(TAG, "Success Response :" + followersList);
                } else {
                    Log.d(TAG, "getFollowersList fail");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Followers List failure :");
            }
        });
    }

    public LiveData<List<User>> getFollowersListLiveData() {
        return followersListLiveData;
    }

    public LiveData<List<Post>> getPostListLiveData() {
        return postListLiveData;
    }




}
