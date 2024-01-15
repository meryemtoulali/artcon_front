package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Picture;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.artcon_test.fragment.setupProfilepicture;
import com.example.artcon_test.model.LoginResponse;
import com.example.artcon_test.model.RegisterRequest;
import com.example.artcon_test.model.UpdateUserViewModel;
import com.example.artcon_test.network.UserService;
import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.ui.signup.SignupActivity;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.example.artcon_test.viewmodel.UserViewModel;
import com.google.firebase.firestore.FirebaseFirestore;


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSetup extends AppCompatActivity {
    private PreferenceManager preferenceManager;


    // Variables
    int step = 1;
    UpdateUserViewModel user;
    RequestBody type;
    RequestBody title;
    MultipartBody.Part picturePart;

    ImageView arrowback;
    private String userId;
    private String TAG = "hatsunemiku";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(R.layout.activity_profile_setup);
        user = new ViewModelProvider(this).get(UpdateUserViewModel.class);
        arrowback = findViewById(R.id.imageView);
        SharedPreferences preferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        userId=preferences.getString("userId",null);
        Log.d(TAG, "AuthPrefs isLoggedIn:" + preferences.getBoolean("isLoggedIn",false));
        Log.d(TAG, "AuthPrefs userId:" + preferences.getString("userId",null));
        Log.d(TAG, "AuthPrefs username:" + preferences.getString("username",null));
        Log.d(TAG, "AuthPrefs token:" + preferences.getString("token",null));

        arrowback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.setup_profile_fragment, new setupProfilepicture(), null)
                    .commit();
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
                .replace(R.id.setup_profile_fragment, fragment)
                .commit();
    }
    // update user
    public void updateUser(){
        UserService userService = UserViewModel.updateUserService();


        if ( user.getPicture() != null){
            picturePart = prepareFilePart("picture", user.getPicture());
        }

        type = RequestBody.create(MediaType.parse("text/plain"),user.getType());

        if ( user.getTitle() != null) {
            title = RequestBody.create(MediaType.parse("text/plain"),user.getTitle());
        }

        Call<Void> setupProfile = userService.setupProfile(userId,picturePart,title,type);

        setupProfile.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.toString());
                if (response.isSuccessful()){
                    Log.d("Update user","OK");
                    Toast.makeText(ProfileSetup.this, "User updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileSetup.this, MainNavActivity.class);
                    startActivity(intent);
                    finish();



                } else {
                    Log.d("Update user","Fail");
                    Log.d("Fail", String.valueOf(response.errorBody()));
                    Toast.makeText(ProfileSetup.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("User update", "Error: " + t.getMessage());
                Toast.makeText(ProfileSetup.this, "Huge Error", Toast.LENGTH_SHORT).show();
            }
        });




        Call<Void> selectinterests = userService.selectInterests(userId,user.getInterestIds());
        selectinterests.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.toString());
                if (response.isSuccessful()){
                    Log.d("Update interests","OK");
                    Toast.makeText(ProfileSetup.this, "Interest selected", Toast.LENGTH_SHORT).show();


                } else {
                    Log.d("Update interests","Fail");
                    Log.d("Fail", String.valueOf(response.errorBody()));
                    Toast.makeText(ProfileSetup.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Update interests", "Error: " + t.getMessage());
                Toast.makeText(ProfileSetup.this, "Huge Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String partName, File file) {
        if (file == null){
            return null;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}