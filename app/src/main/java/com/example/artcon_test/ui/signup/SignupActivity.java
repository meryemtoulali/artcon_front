package com.example.artcon_test.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.artcon_test.ProfileSetup;
import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.R;
import com.example.artcon_test.model.LoginResponse;
import com.example.artcon_test.model.RegisterRequest;
import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.example.artcon_test.viewmodel.LoginViewModel;
import com.example.artcon_test.viewmodel.SignupViewModel;
import com.example.artcon_test.databinding.ActivitySignupBinding;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    String TAG="hatsunemiku";
    SignupViewModel signupViewModel;
    private ActivitySignupBinding binding;
    private PreferenceManager preferenceManager;


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
        Log.d(TAG, "perform signup called");

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
        Log.d(TAG, "register request:" + registerRequest.toString());
        Call<LoginResponse> call = authService.register(registerRequest);
        Log.d(TAG,"register call : " + call.toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println(response);
                if (response.isSuccessful()){
                    Log.d(TAG,"register successful: " + response);

                    LoginResponse loginResponse = response.body();
                    handleRequestResponse(loginResponse);
                    // Assuming you have collected all the user information and created a RegisterRequest object
                    RegisterRequest registerRequest = new RegisterRequest(
                             firstName, lastName, gender, phonenumber, birthday, location, username, email, password
                    );

// Call the saveUserToFirestore function and pass the RegisterRequest object as an argument
                    saveUserToFirestore(registerRequest,loginResponse);

                } else {
                    Log.d(TAG,"register failed: " + response);
                    Toast.makeText(SignupActivity.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "Register failed: " + t.getMessage());
                Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToFirestore(RegisterRequest registerRequest, LoginResponse loginResponse) {
        Log.d(TAG, "Saving user data to Firestore: " + registerRequest.toString());

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_TOKEN, loginResponse.getToken());
        user.put(Constants.KEY_USER_ID, loginResponse.getUserId());
        user.put(Constants.KEY_USERNAME, registerRequest.getUsername());
        user.put(Constants.KEY_EMAIL, registerRequest.getEmail());
        user.put(Constants.KEY_FIRSTNAME, registerRequest.getFirstname());
        user.put(Constants.KEY_LASTNAME, registerRequest.getLastname());
        user.put(Constants.KEY_GENDER, registerRequest.getGender());
        user.put(Constants.KEY_PHONENUMBER, registerRequest.getPhonenumber());
        user.put(Constants.KEY_LOCATION, registerRequest.getLocation());


        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(SignupActivity.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                    Toast.makeText(SignupActivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                });
    }

    private void handleRequestResponse(LoginResponse loginResponse) {
        if (!loginResponse.getToken().isEmpty()) {
            SharedPreferences preferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", loginResponse.getToken());
            editor.putString("userId", loginResponse.getUserId()); // Save userId to SharedPreferences
            editor.putString("username", loginResponse.getUsername());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileSetup.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
        }
    }
}