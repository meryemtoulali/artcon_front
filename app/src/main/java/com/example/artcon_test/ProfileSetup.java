package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.artcon_test.fragment.setupAccounttype;
import com.example.artcon_test.fragment.setupInterests;
import com.example.artcon_test.fragment.setupProfilepicture;
import com.example.artcon_test.model.UpdateUserRequest;
import com.example.artcon_test.model.UpdateUserViewModel;
import com.example.artcon_test.network.UserService;
import com.example.artcon_test.viewmodel.UserViewModel;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSetup extends AppCompatActivity {

    // Variables
    int step = 1;
    UpdateUserViewModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        user = new ViewModelProvider(this).get(UpdateUserViewModel.class);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.setup_profile_fragment, new setupProfilepicture(), null)
                    .commit();
        }


//        loadFragment(new setupProfilepicture());
//        TextView next_button = findViewById(R.id.skip_button);
        Button next_button = findViewById(R.id.next_button);
        next_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (step == 1){
                            loadFragment(new setupAccounttype());
                            step = 2;
                        }
                        else if (step == 2){
                            loadFragment(new setupInterests());
                            step = 3;
                        }
                        else if (step == 3){
                            updateUser();
                        }
                    }
                }
        );

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
                .replace(R.id.setup_profile_fragment, fragment)
                .commit();
    }
    // update user
    private void updateUser(){
        UserService userService = UserViewModel.updateUserService();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();


        updateUserRequest.setTitle(user.getTitle());
        updateUserRequest.setType(user.getType());

        MultipartBody.Part picturePart = prepareFilePart("picture", user.getPicture());

        RequestBody type = RequestBody.create(MediaType.parse("text/plain"),user.getType());
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),user.getTitle());

        Log.d("Update User","Data "+ updateUserRequest.getTitle() +" " + updateUserRequest.getType());
        Log.d("Update User","Update UpdateUserRequest called");
        Call<Void> call = userService.setupProfile(1,picturePart,title,type);
        Log.d("Update User","register call : " + call.toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.toString());
                if (response.isSuccessful()){
                    Log.d("Update user","OK");
                    Toast.makeText(ProfileSetup.this, "User updated", Toast.LENGTH_SHORT).show();

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
    }

    private MultipartBody.Part prepareFilePart(String partName, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}