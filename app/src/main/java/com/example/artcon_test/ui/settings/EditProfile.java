package com.example.artcon_test.ui.settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.artcon_test.R;
import com.example.artcon_test.model.UpdateUserViewModel;
import com.example.artcon_test.network.UserService;
import com.example.artcon_test.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends Fragment {

    private EditProfileViewModel mViewModel;
    private String USER_ID;
    private UpdateUserViewModel userViewModel;
    private ImageView pdp;
    private ImageView coverimage;

    private MultipartBody.Part picturePart;
    private MultipartBody.Part bannerPart;
    private RequestBody titleRequestBody;
    private RequestBody usernameRequestBody;
    private RequestBody firstnameRequestBody;
    private RequestBody lastnameRequestBody;
    private RequestBody phonenumberRequestBody;
    private RequestBody bioRequestBody;
    private RequestBody emailRequestBody;

    EditText firstnameEditText;
    EditText lastnameEditText;
    EditText usernameEditText;
    EditText titleEditText;
    EditText phonenumberEditText;
    EditText bioEditText;
    EditText emailEditText;

    public static EditProfile newInstance() {
        return new EditProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

//        ImageView btnBack = view.findViewById(R.id.buttonBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                navigateToFragment(R.id.);
//            }
//        });

        pdp = view.findViewById(R.id.pfpImage);
        coverimage = view.findViewById(R.id.coverImage);
        firstnameEditText = view.findViewById(R.id.firstname);
        lastnameEditText = view.findViewById(R.id.lastname);
        usernameEditText = view.findViewById(R.id.username);
        titleEditText = view.findViewById(R.id.title);
        phonenumberEditText = view.findViewById(R.id.phonenumber);
        bioEditText = view.findViewById(R.id.bio);
        emailEditText = view.findViewById(R.id.email);
        Button saveButton = view.findViewById(R.id.saveButton);
        Picasso.get().setLoggingEnabled(true);

        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);

        USER_ID = preferences.getString("userId", null);

        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        mViewModel.getUserById(USER_ID);

        pdp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage(1);
                    }
                }
        );

        coverimage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage(2);
                    }
                }
        );

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUser();
                    }
                }
        );

        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
//            isArtist = "artist".equals(userViewModel.getType());
//            Log.d(TAG, "isArtist:" + isArtist);

            if (user != null) {
                // Update UI with userViewModel data
                Picasso.get()
                        .load(user.getPicture())
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(pdp);
                Picasso.get()
                        .load(user.getBanner())
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(coverimage);
                firstnameEditText.setText(user.getFirstname());
                lastnameEditText.setText(user.getLastname());
                usernameEditText.setText(user.getUsername());
                emailEditText.setText(user.getEmail());
                if (user.getBio() != null){
                    bioEditText.setText(user.getBio());
                }
                if (user.getTitle() != null){
                    titleEditText.setText(user.getTitle());
                }
                phonenumberEditText.setText(user.getPhoneNumber());
            }
        });
        return view;
    }

    private void navigateToFragment(int fragmentId) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(fragmentId);
    }

    public void updateUser(){
        UserService userService = UserViewModel.updateUserService();

        if ( userViewModel.getPicture() != null){
            picturePart = prepareFilePart("picture", userViewModel.getPicture());
        }
        if (userViewModel.getBanner() != null){
            bannerPart = prepareFilePart("banner",userViewModel.getBanner());
        }

        titleRequestBody = RequestBody.create(MediaType.parse("text/plain"),getTitle());
        phonenumberRequestBody = RequestBody.create(MediaType.parse("text/plain"),getPhone());
        bioRequestBody = RequestBody.create(MediaType.parse("plain/text"),getBio());
        usernameRequestBody = RequestBody.create(MediaType.parse("plain/text"),getUsername());
        lastnameRequestBody = RequestBody.create(MediaType.parse("plain/text"),getLastName());
        firstnameRequestBody = RequestBody.create(MediaType.parse("plain/text"),getFirstName());
        emailRequestBody = RequestBody.create(MediaType.parse("plain/text"),getEmail());

        Call<Void> editprofile = userService.editprofile(USER_ID,picturePart,bannerPart,titleRequestBody,usernameRequestBody,firstnameRequestBody,lastnameRequestBody,phonenumberRequestBody,bioRequestBody,emailRequestBody);

        editprofile.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.toString());
                if (response.isSuccessful()){
                    Log.d("Update user","OK");
                    Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("Update user","Fail");
                    Log.d("Fail", String.valueOf(response.errorBody()));
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("User update", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Huge Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chooseImage(int imageIndex) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (imageIndex == 1) {
            pickImageLauncher1.launch(galleryIntent);
        } else if (imageIndex == 2) {
            pickImageLauncher2.launch(galleryIntent);
        }
    }

    private final ActivityResultLauncher<Intent> pickImageLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                handleImageResult(result, 1);
            }
    );

    private final ActivityResultLauncher<Intent> pickImageLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                handleImageResult(result, 2);
            }
    );

    private void handleImageResult(ActivityResult result, int imageIndex) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            // Handle the selected image URI
            Uri selectedImageUri = result.getData().getData();
            // Now you can use the selectedImageUri to do whatever you need

            if (imageIndex == 1) {
                pdp.setImageURI(selectedImageUri);
                Log.d("File test URI", String.valueOf(selectedImageUri));
                File selectedImageFile = uriToFile(selectedImageUri, getContext());
                Log.d("File test", String.valueOf(selectedImageFile));
                // MultipartBody.Part picturePart = prepareFilePart("picture", selectedImageFile);
                userViewModel.setPicture(selectedImageFile);
            } else if (imageIndex == 2) {
                // Handle the second image selection
                coverimage.setImageURI(selectedImageUri);
                Log.d("File test URI", String.valueOf(selectedImageUri));
                File selectedImageFile = uriToFile(selectedImageUri, getContext());
                Log.d("File test", String.valueOf(selectedImageFile));
                // MultipartBody.Part picturePart = prepareFilePart("picture", selectedImageFile);
                userViewModel.setBanner(selectedImageFile);
            }
        }
    }

    // Function to convert Uri to File
    private File uriToFile(Uri uri, Context context) {
        String filePath = "";
        if (uri.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(column_index);
                cursor.close();
            }
        } else if (uri.getScheme().equals("file")) {
            filePath = uri.getPath();
        }
        return new File(filePath);
    }

    private MultipartBody.Part prepareFilePart(String partName, File file) {
        if (file == null){
            return null;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    public String getUsername() {
        if (usernameEditText != null) {
            return usernameEditText.getText().toString();
        } else {
            return null;
        }
    }

    public String getBio() {
        if (bioEditText != null) {
            return bioEditText.getText().toString();
        } else {
            return null;
        }
    }

    public String getTitle() {
        if (titleEditText != null) {
            return titleEditText.getText().toString();
        } else {
            return null;
        }
    }

    public String getFirstName() {
        if (firstnameEditText != null) {
            return firstnameEditText.getText().toString();
        } else {
            return null;
        }
    }
    public String getLastName() {
        if (lastnameEditText != null) {
            return lastnameEditText.getText().toString();
        } else {
            return null;
        }
    }

    // Method to get the phone number from the EditText field
    public String getPhone() {
        if (phonenumberEditText != null) {
            return phonenumberEditText.getText().toString();
        } else {
            return null; // Handle the case when the phoneEditText is not initialized
        }
    }

    public String getEmail() {
        if (emailEditText != null) {
            return emailEditText.getText().toString();
        } else {
            return null; // Handle the case when the phoneEditText is not initialized
        }
    }
}