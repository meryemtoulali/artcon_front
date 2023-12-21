package com.example.artcon_test.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> navigateToNextChat = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getNavigateToChat() {
        return navigateToNextChat;
    }

    public void onFabClick() {
        // Set the LiveData to true to signal the navigation action
        navigateToNextChat.setValue(true);
    }
}