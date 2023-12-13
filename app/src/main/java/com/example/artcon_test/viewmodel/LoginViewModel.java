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
import com.example.artcon_test.model.LoginResponse;
import com.example.artcon_test.model.LoginRequest;
public class LoginViewModel extends ViewModel {
    private ApiService apiService;

    private MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    // Default constructor
    public LoginViewModel() {
        // Provide the base URL for your Spring Boot backend
        this("http://192.168.1.16:8080/user/");
    }

    // Constructor with a custom base URL
    public LoginViewModel(String baseUrl) {
        Log.d("LoginViewModel", "Base URL: " + baseUrl);

        apiService = new Retrofit.Builder()
                .baseUrl("http://192.168.1.16:8080/user/")
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

    // Adjusted login method to use LoginRequest instead of UpdateUserRequest
    public void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.login(loginRequest)
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