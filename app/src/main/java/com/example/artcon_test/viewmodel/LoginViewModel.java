package com.example.artcon_test.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.artcon_test.network.ApiService;
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;

public class LoginViewModel extends ViewModel {
    private ApiService apiService;

    private MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    // Default constructor
    public LoginViewModel() {
        // Provide a default base URL or initialize it in a way that makes sense for your app
        this("your_default_base_url");
    }

    // Constructor with a custom base URL
    public LoginViewModel(String baseUrl) {
        if (!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://")) {
            // If the provided URL doesn't have a scheme, add a default one (e.g., http://)
            baseUrl = "http://" + baseUrl;
        }

        apiService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public MutableLiveData<LoginResponse> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        apiService.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> loginResult.setValue(result),
                        throwable -> {
                            Log.e("LoginViewModel", "Error: " + throwable.getMessage());
                            error.setValue("Login failed. Please try again."); // Provide a user-friendly error message
                        }
                );
    }
}
