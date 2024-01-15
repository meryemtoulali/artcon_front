package com.example.artcon_test.ui.settings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.UserRepository;

public class EditProfileViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void getUserById(String userId) {
        Log.d("Edit Profile", "called getUserById");

        userRepository.getUserById(userId, new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.postValue(user);
                Log.d("Edit Profile", "updated userLiveData");
//                Log.d(TAG, user.toString());

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Edit Profile", errorMessage);
            }
        });
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

}