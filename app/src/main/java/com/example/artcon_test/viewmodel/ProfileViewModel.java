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





    // Method to fetch user data
    public void getUserById(String userId) {
        Log.d(TAG, "called getUserById");

        userRepository.getUserById(userId, new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Update LiveData with the retrieved user data
                userLiveData.postValue(user);
                Log.d(TAG, "updated userLiveData");
//                Log.d(TAG, user.toString());

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
                if (postList != null) {
                    for (Post post : postList) {
                        Log.d(TAG, "Post in livedata: " + post.toString() +"\n\n");
                    }
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    public LiveData<List<Post>> getPostListLiveData() {
        return postListLiveData;
    }




}
