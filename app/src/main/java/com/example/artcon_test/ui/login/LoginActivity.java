package com.example.artcon_test.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.ui.ForgotPasswordActivity;
import com.example.artcon_test.R;
import com.example.artcon_test.ui.signup.SignupActivity;
import com.example.artcon_test.model.LoginRequest;
import com.example.artcon_test.network.AuthService;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.example.artcon_test.viewmodel.LoginViewModel;
import com.example.artcon_test.model.LoginResponse;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;

import java.util.HashMap;

import android.graphics.drawable.Drawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.artcon_test.databinding.ActivityLoginBinding;
import com.google.firebase.firestore.SetOptions;


public class LoginActivity extends AppCompatActivity {
    String TAG = "hatsunemiku";
    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize PreferenceManager
        preferenceManager = new PreferenceManager(getApplicationContext());



        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        TextView forgotPassword = findViewById(R.id.forgot_pwd);
        TextView signupTextView = findViewById(R.id.signup);

        AuthService authService = LoginViewModel.getAuthService();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                LoginRequest loginRequest = new LoginRequest(username,password);

        loginButton.setOnClickListener(
            if(!username.isEmpty() && !password.isEmpty()){
                showButtonClickIndicator(loginButton);

                Call<LoginResponse> call = authService.login(loginRequest);
                Log.d(TAG,call.toString());


                call.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            Log.d(TAG, "login successful: " + response);
                            LoginResponse loginResponse = response.body();
                            handleLoginResponse(loginResponse);
                            Log.d(TAG, "Token_Firebase: " + loginResponse.getToken());
                            Log.d(TAG, "UserId_Firebase: " + loginResponse.getUserId());
                            // Add data to Firestore after successful login.
                            addDataToFirestore(loginResponse);
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
            } else {
                Toast.makeText(LoginActivity.this, "Fill the fields", Toast.LENGTH_SHORT).show();
            }
        });

        signupTextView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String username = binding.editTextUsername.getText().toString().trim();

        // Query Firestore for user data based on the username
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_USERNAME, username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                        // Save user data to PreferenceManager
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_FIRSTNAME, documentSnapshot.getString(Constants.KEY_FIRSTNAME));
                        preferenceManager.putString(Constants.KEY_LASTNAME, documentSnapshot.getString(Constants.KEY_LASTNAME));
                        preferenceManager.putString(Constants.KEY_USERNAME, documentSnapshot.getString(Constants.KEY_USERNAME));

                    } else {
                        // Handle user not found or other errors
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }


        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void showButtonClickIndicator(Button loginButton) {
        Drawable originalBackground = loginButton.getBackground();
        loginButton.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_button));
        new Handler().postDelayed(() -> loginButton.setBackground(originalBackground), 1000);
    }

    private void handleLoginResponse(LoginResponse loginResponse) {
        if (!loginResponse.getToken().isEmpty()) {

            SharedPreferences preferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", loginResponse.getToken());
            editor.putString("userId", loginResponse.getUserId());
            editor.putString("username", loginResponse.getUsername());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
            // Set the user ID in PreferenceManager
            preferenceManager.putString(Constants.KEY_USER_ID, loginResponse.getUserId());

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainNavActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }



    private void addDataToFirestore(LoginResponse loginResponse) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_TOKEN, loginResponse.getToken());
        user.put(Constants.KEY_USER_ID, loginResponse.getUserId());
        Log.d(TAG, "Data to be updated: " + user.toString());

        String userId = loginResponse.getUserId(); // Get the userId from loginResponse

        Log.d(TAG, "Updating Firestore document with userId: " + userId);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(userId)
                .set(user, SetOptions.merge()) // Using merge to update only the specified fields
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(getApplicationContext(), "Error updating document", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error updating Firestore document: " + exception.getMessage());
                });




    }



}