package com.example.artcon_test.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.artcon_test.model.User;
import com.example.artcon_test.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private UserRepository userRepository;
    MutableLiveData<List<User>> searchPeopleLiveData = new MutableLiveData<>();
    private String currentSearchType = "people"; // Default search type
    String TAG = "AllTooWell";

    public SearchViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<List<User>> searchPeople(String query) {
        userRepository.searchPeople(query).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    searchPeopleLiveData.setValue(users);
                    Log.d(TAG, "Success Response :" + users);
                } else {
                    Log.d(TAG, "fail MutableLiveData :");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Huge error :");
            }
        });
        return searchPeopleLiveData;
    }
    public String getCurrentSearchType() {
        return currentSearchType;
    }

    public void setCurrentSearchType(String searchType) {
        this.currentSearchType = searchType;
        Log.d(TAG, "setCurrentSearchType: " +searchType );
    }
}