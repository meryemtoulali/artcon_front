package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.model.LoginResponse;
import com.example.artcon_test.model.RegisterRequest;
import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.viewmodel.LoginViewModel;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ImageView arrowBack = findViewById(R.id.arrow_back);
        AuthService authService = LoginViewModel.getAuthService();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // This will simulate a back button press
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SignUpFirstFragment())
                    .commit();
        }
    }
    public void navigateToSecondStep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new SignUpSecondFragment())
                .addToBackStack(null)
                .commit();
    }

    public void performSignUp() {
        String username = ((SignUpFirstFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getUsername();
        String password = ((SignUpFirstFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getPassword();
        String email = ((SignUpFirstFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getEmail();
        String firstName = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getFirstName();
        String lastName = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getLastName();
        Date birthday = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getBirthday();
        String location = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getLocation();
        String gender = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getGender();
        String phonenumber = ((SignUpSecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .getPhone();

        AuthService authService = LoginViewModel.getAuthService();
        RegisterRequest registerRequest = new RegisterRequest(firstName,lastName,gender,phonenumber,birthday,email,password,location,username);
        Call<LoginResponse> call = authService.register(registerRequest);
        Log.d("slay",call.toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    handleRequestResponse(loginResponse);
                } else {
                    Toast.makeText(SignupActivity.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("logactiv", "Error: " + t.getMessage());
                Toast.makeText(SignupActivity.this, "Huge Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleRequestResponse(LoginResponse loginResponse) {
        if (loginResponse.isSuccess()) {
            String token = loginResponse.getToken();
            Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoggedActivity.class);
            startActivity(intent);
            finish();
        } else {
            String errorMessage = loginResponse.getMessage();
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}