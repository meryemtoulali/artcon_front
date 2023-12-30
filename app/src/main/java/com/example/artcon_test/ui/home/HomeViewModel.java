package com.example.artcon_test.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.PostRepository;
import com.example.artcon_test.repository.UserRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    String TAG = "hatsunemiku";

    private final UserRepository userRepository = new UserRepository();
    private final PostRepository postRepository = new PostRepository();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> HomeLiveData = new MutableLiveData<>();
    private MutableLiveData<Post> selectedPostLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    //Posts

    public void getHome() {
        userRepository.getHome(new PostRepository.PostCallback() {
            @Override
            public void onSuccess(List<Post> postList) {
                HomeLiveData.postValue(postList);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    public LiveData<List<Post>> getHomeLiveData() {
        return HomeLiveData;
    }
}