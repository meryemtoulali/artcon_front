package com.example.artcon_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.network.ApiService;
import com.example.artcon_test.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePost extends AppCompatActivity {

    AutoCompleteTextView textCategory;

    String category;

    TextInputEditText PostDescription;
    //TextInputEditText postEditText;
    ImageView imagePost;
    //ImageView docPost;
    String descriptionText;
    Uri selectedImageUri;
    String path;

    VideoView videoPost;
    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    int SELECT_VIDEO = 200;
    int SELECT_DOC =200;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

       textCategory = findViewById(R.id.textCategory);
        //postEditText = findViewById(R.id.textPostDescription);
        imagePost = findViewById(R.id.imageView);
        ImageView videoPost = findViewById(R.id.videoView);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories, // Assuming you have defined this array in your resources
                android.R.layout.simple_dropdown_item_1line
        );

        // Set the adapter for the AutoCompleteTextView
        textCategory.setAdapter(adapter);

        // Set an item click listener for the AutoCompleteTextView
        textCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected text
                category = parent.getItemAtPosition(position).toString();

                // Do something with the selected text
                Toast.makeText(CreatePost.this, "Selected: " + category, Toast.LENGTH_SHORT).show();
            }
        });

        PostDescription = findViewById(R.id.textPostDescription);
        PostDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                descriptionText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //PostDescription = findViewById(R.id.textPostDescription);
        //String descriptionText = PostDescription.getText().toString();

        // handle the Choose Image button to trigger
        // the image chooser function
        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageChooser();
            }
        });

       videoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoChooser();
            }
        });
       // add post click
        Button btnAddPost = findViewById(R.id.buttonAddPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aha", category);
                Log.d("aha", descriptionText);
                addPost(category, descriptionText);
            }
        });


    } //oncreate end

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    void videoChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("video/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_VIDEO);
    }


    ////
    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    Context context = CreatePost.this;
                    path = RealPathUtil.getRealPath(context,selectedImageUri);
                    imagePost.setImageURI(selectedImageUri);
                }
            }
        }
    }
    public void addPost (String category, String descriptionText){
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part req_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody req_category = RequestBody.create(MediaType.parse("multipart/form-data"),category);
        RequestBody req_descriptionText = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionText);
        String username = "khadija";
        apiService.addpost(username, req_category,req_descriptionText, req_image)
                .enqueue(new Callback<AddPostRes>() {
                    @Override
                    public void onResponse(Call<AddPostRes> call, Response<AddPostRes> response) {
                        if (response.isSuccessful()) {

                            if (response.body().getStatus().toString().equals("200")) {
                                Toast.makeText(getApplicationContext(), "Post Added Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "not Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPostRes> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}// class end