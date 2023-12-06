package com.example.artcon_test.viewmodel;

// UserProfileViewModel.java

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.UserRepository;

public class ProfileViewModel extends ViewModel {
    String TAG = "ProfileActivity//";

    private final UserRepository userRepository = new UserRepository();

    // LiveData for observing the user data
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    // Method to fetch user data

    public void getUserById(String userId) {
        Log.d(TAG, "called getUserById");

        userRepository.getUserById(userId, new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Update LiveData with the retrieved user data
                userLiveData.postValue(user);
                Log.d(TAG, "successfully posted to userLiveData :");
                Log.d(TAG, user.toString());

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

}
