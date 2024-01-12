package com.example.artcon_test.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.artcon_test.R;
import com.example.artcon_test.ui.login.LoginActivity;
import com.example.artcon_test.ui.signup.SignupActivity;


public class OnBoardingActivity extends AppCompatActivity {
    String TAG="hatsunemiku";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        SharedPreferences preferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        Log.d(TAG, "AuthPrefs isLoggedIn:" + preferences.getBoolean("isLoggedIn",false));
        Log.d(TAG, "AuthPrefs userId:" + preferences.getString("userId",null));
        Log.d(TAG, "AuthPrefs username:" + preferences.getString("username",null));
        Log.d(TAG, "AuthPrefs token:" + preferences.getString("token",null));

        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(OnBoardingActivity.this, MainNavActivity.class);
            startActivity(intent);
            finish();
        } else {
            Button loginButton = findViewById(R.id.buttonLogin);
            Button signupButton = findViewById(R.id.buttonSignup);
            loginButton.setOnClickListener(view -> {
                Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(intent);
            });
            signupButton.setOnClickListener(view -> {
                Intent intent = new Intent(OnBoardingActivity.this, SignupActivity.class);
                startActivity(intent);
            });
        }
    }
}