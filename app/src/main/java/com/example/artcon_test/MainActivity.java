package com.example.artcon_test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.artcon_test.viewmodel.LoginViewModel;
import com.example.artcon_test.model.LoginResponse;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            viewModel.login(username, password);
        });

//        viewModel.getLoginResult().observe(this, this::handleLoginResult);
        Intent intent = new Intent(this, ProfileSetup.class);
        startActivity(intent);
    }

    private void handleLoginResult(LoginResponse result) {
        // Check if the login was successful
        if (result.isSuccess()) {
            // Show a toast message
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Navigate to another activity
            Intent intent = new Intent(this, ProfileSetup.class);
            startActivity(intent);
            finish(); // Optional: finish the current activity to prevent going back with the back button
        } else {
            // If login failed, show an error message
            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}