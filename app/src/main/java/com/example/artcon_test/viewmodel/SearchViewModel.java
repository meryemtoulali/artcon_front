package com.example.artcon_test.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.PostRepository;
import com.example.artcon_test.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    MutableLiveData<List<User>> searchPeopleLiveData = new MutableLiveData<>();

    public MutableLiveData<List<User>> getSearchPeopleLiveData() {
        return searchPeopleLiveData;
    }

    public MutableLiveData<List<Post>> getSearchPostsLiveData() {
        return searchPostsLiveData;
    }

    MutableLiveData<List<Post>> searchPostsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private String currentSearchType = "people"; // Default search type
    String TAG = "AllTooWell";

    public SearchViewModel() {
        userRepository = new UserRepository();
        postRepository = new PostRepository();
    }
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }
    public LiveData<List<User>> searchPeople(String query) {
        userRepository.searchPeople(query).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    searchPeopleLiveData.setValue(users);
                    Log.d(TAG, "Success Response :" + users);
                    toastMessage.setValue("Success Response");
                } else {
                    Log.d(TAG, "fail MutableLiveData :");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                toastMessage.setValue("Network Error");
                Log.d(TAG, "Huge error :");
            }
        });
        return searchPeopleLiveData;
    }

    public LiveData<List<Post>> searchPosts(String query) {
        postRepository.searchPosts(query).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    searchPostsLiveData.setValue(posts);
                    Log.d(TAG, "Success Response :" + posts);
                    toastMessage.setValue("Success Response");
                } else {
                    Log.d(TAG, "fail MutableLiveData :");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                toastMessage.setValue("Network Error");
                Log.d(TAG, "Huge error :");
            }
        });
        return searchPostsLiveData;
    }


    public String getCurrentSearchType() {
        return currentSearchType;
    }
    public void setCurrentSearchType(String searchType) {
        this.currentSearchType = searchType;
        Log.d(TAG, "setCurrentSearchType: " +searchType );
    }
}