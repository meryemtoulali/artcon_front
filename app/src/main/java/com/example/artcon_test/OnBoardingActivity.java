package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



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

//        SharedPreferences.Editor editor = preferences.edit();
////        editor.putString("token", "your_token_here");
////        editor.putString("userId", "1");
////        editor.remove("token");
//        editor.putBoolean("isLoggedIn", false);
//        editor.apply();
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(OnBoardingActivity.this, BottomNavbarActivity.class);
            startActivity(intent);
            finish();
        } else {
            Button loginButton = findViewById(R.id.buttonLogin);
            Button signupButton = findViewById(R.id.buttonSignup);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OnBoardingActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
        }


    }


}