package com.example.artcon_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.viewmodel.LoginViewModel;
import com.example.artcon_test.model.LoginResponse;
import android.content.Intent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        TextView forgotPassword = findViewById(R.id.forgot_pwd);
        TextView signupTextView = findViewById(R.id.signup);

        AuthService authService = LoginViewModel.getAuthService();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                LoginRequest loginRequest = new LoginRequest(username,password);


                Call<LoginResponse> call = authService.login(loginRequest);
                Log.d("salma",call.toString());
                call.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            Log.d("salma","response is successful");

                            LoginResponse loginResponse = response.body();
                            handleLoginResponse(loginResponse);
                        } else {
                            Log.d("salma","response is failed");
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("logactiv", "Error: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Fail Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private void handleLoginResponse(LoginResponse loginResponse) {
        if (!loginResponse.getToken().isEmpty()) {
            String token = loginResponse.getToken();
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoggedActivity.class);
            startActivity(intent);
            finish();
        } else {
            String errorMessage = loginResponse.getMessage();
            Toast.makeText(this, "error message", Toast.LENGTH_SHORT).show();
        }
    }
}