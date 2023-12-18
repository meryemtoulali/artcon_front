package com.example.artcon_test.ui.job;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public JobViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Job fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}