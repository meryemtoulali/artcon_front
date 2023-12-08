package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.artcon_test.viewmodel.SignupViewModel;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    SignupViewModel signupViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

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
        Log.d("salma","perform signup called");

        String username = signupViewModel.getUsername();
        String password = signupViewModel.getPassword();
        String email = signupViewModel.getEmail();
        String firstName = signupViewModel.getFirstName();
        String lastName = signupViewModel.getLastName();
        Date birthday = signupViewModel.getBirthday();
        String location = signupViewModel.getLocation();
        String gender = signupViewModel.getGender();
        String phonenumber = signupViewModel.getPhonenumber();

        AuthService authService = LoginViewModel.getAuthService();
        RegisterRequest registerRequest = new RegisterRequest(firstName,lastName,gender,phonenumber,birthday, location, username, email, password);
        System.out.println(registerRequest.toString());
        Call<LoginResponse> call = authService.register(registerRequest);
        Log.d("slay","register call : " + call.toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println(response.toString());
                if (response.isSuccessful()){
                    Log.d("salma","register response is successful!");

                    LoginResponse loginResponse = response.body();
                    handleRequestResponse(loginResponse);
                } else {
                    Log.d("salma","register response has failed!");


                    Toast.makeText(SignupActivity.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("salma", "Error: " + t.getMessage());
                Toast.makeText(SignupActivity.this, "Huge Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleRequestResponse(LoginResponse loginResponse) {
        if (!loginResponse.getToken().isEmpty()) {
            String token = loginResponse.getToken();
            Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoggedActivity.class);
            startActivity(intent);
            finish();
        } else {
            String errorMessage = loginResponse.getMessage();
            Toast.makeText(this, "errorMessage", Toast.LENGTH_SHORT).show();
        }
    }
}