package com.example.artcon_test.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.ui.ForgotPasswordActivity;
import com.example.artcon_test.R;
import com.example.artcon_test.ui.signup.SignupActivity;
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.viewmodel.LoginViewModel;
import com.example.artcon_test.model.LoginResponse;
import android.content.Intent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    String TAG = "hatsunemiku";
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
                Log.d(TAG,call.toString());
                call.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            Log.d(TAG, "login successful: " + response);
                            LoginResponse loginResponse = response.body();
                            handleLoginResponse(loginResponse);
                        } else {
                            Log.d(TAG,"Login failed:" + response);
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e(TAG,"Login failed:: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
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
//            setContentView(R.layout.activity_onboarding);

            SharedPreferences preferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", loginResponse.getToken());
            editor.putString("userId", loginResponse.getUserId());
            editor.putString("username", loginResponse.getUsername());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainNavActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}