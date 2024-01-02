package com.example.artcon_test;

import static android.util.Pair.create;
import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.model.Category;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.network.ApiService;
import com.example.artcon_test.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class CreatePost extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<Uri> uri = new ArrayList<>();
    RecyclerAdapter adapterImages;

    private static final int Read_Permission = 101;

    AutoCompleteTextView textCategory;

    String category;

    List<Interest> interestList;

    TextInputEditText PostDescription;
    //TextInputEditText postEditText;
    ImageView imagePost;
    //ImageView docPost;
    String descriptionText;
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_VIDEO = 2;

    long selectedInterestId;
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

        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        Call<List<Interest>> call = apiService.getAllInterests();
        call.enqueue(new Callback<List<Interest>>() {
            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                if (response.isSuccessful()) {
                    List<Interest> interests = response.body();
                    // Log the interests
                   /* for (Interest interest : interests) {
                        Log.d("Interest", "working get interest");
                        Log.d("Interest", "ID: " + interest.getId() + ", Name: " + interest.getInterest_name());
                    }*/

                    List<String> interestNames = new ArrayList<>();
                    for (Interest interest : interests) {
                        interestNames.add(interest.getInterest_name());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreatePost.this, android.R.layout.simple_dropdown_item_1line, interestNames);
                    AutoCompleteTextView autoCompleteTextView = findViewById(R.id.textCategory);
                    autoCompleteTextView.setAdapter(adapter);

                    // Store the list of interests for later reference
                    interestList = interests;
                } else {
                    // Handle error
                    Log.d("Interest", "response is not succeed");
                }
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.d("Interest", "OnFailure");

            }

        });


        // Set an item click listener for the AutoCompleteTextView
        textCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected text
                category = parent.getItemAtPosition(position).toString();
                // Do something with the selected text
                Toast.makeText(CreatePost.this, "Selected: " + category, Toast.LENGTH_SHORT).show();

                //jadid
                // Get the selected text
                String selectedInterestName = parent.getItemAtPosition(position).toString();

                Log.d("category", "category name is : "+ selectedInterestName);
                // Find the corresponding interest ID
                selectedInterestId = findInterestIdByName(selectedInterestName);

                Log.d("category", "category id is : "+ selectedInterestId);
                // Do something with the selected interest ID
                Toast.makeText(CreatePost.this, "Selected Interest ID: " + selectedInterestId, Toast.LENGTH_SHORT).show();
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


        recyclerView = findViewById(R.id.recyclerView_Gallery_Images);

        adapterImages = new RecyclerAdapter(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(CreatePost.this, 3));
        recyclerView.setAdapter(adapterImages);

        //Permission
        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Vérification des autorisations
                if (ContextCompat.checkSelfPermission(CreatePost.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission si elle n'est pas accordée
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            Read_Permission);
                } else {
                    imageChooser();
                }
                adjustRecyclerViewHeight();
            }
        });

       videoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérification des autorisations
                if (ContextCompat.checkSelfPermission(CreatePost.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission si elle n'est pas accordée
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            Read_Permission);
                } else {
                    videoChooser();
                }
                adjustRecyclerViewHeight();
            }
        });
       // add post click
        Button btnAddPost = findViewById(R.id.buttonAddPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Long interest_id = selectedInterestId;
                String description = descriptionText;
                //
                Log.d("aha", "id : "+ selectedInterestId);
                Log.d("aha", "description: "+ descriptionText);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    addPost(12, descriptionText, uri, interest_id);
                }
            }
        });


    } //oncreate end

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }
    void videoChooser() {
        Intent i = new Intent();
        i.setType("video/*");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Video"), 2);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
Log.d("upload","enter the result");
        if (requestCode == VIEW_TYPE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("upload","type ok");
            if (data.getClipData() != null) {
                Log.d("upload","get def de 0");
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Log.d("upload","inside for images");
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uri.add(imageUri);
                    Log.d("upload","uri size "+uri.size());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterImages.notifyDataSetChanged();
                    }
                });
               // adapterImages.notifyDataSetChanged();
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                uri.add(imageUri);
                adapterImages.notifyDataSetChanged();
            }
        } else if (requestCode == VIEW_TYPE_VIDEO && resultCode == Activity.RESULT_OK && data != null) {
            //
            Log.d("upload","type Vedio ok");
            if (data.getClipData() != null) {
                Log.d("upload","get data def de 0");
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Log.d("upload","inside for vedio");
                    Uri videoUri = data.getClipData().getItemAt(i).getUri();
                    uri.add(videoUri);
                    Log.d("upload","uri vedio size "+uri.size());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterImages.notifyDataSetChanged();
                    }
                });
                // adapterImages.notifyDataSetChanged();
            } else if (data.getData() != null) {
                Uri videoUri = data.getData();
                uri.add(videoUri);
                adapterImages.notifyDataSetChanged();
            }

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addPost (Integer userId, String descriptionText, ArrayList<Uri> imageUris, Long interestId){
        RetrofitService retrofitService = new RetrofitService();
        ApiService apiService = retrofitService.getRetrofit().create(ApiService.class);

        // Prepare the request body
        List<MultipartBody.Part> mediafiles = new ArrayList<>();
        for (Uri uri : imageUris) {
            File file = new File(RealPathUtil.getRealPath(CreatePost.this, uri));
            //
            // Check the file extension
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
            Log.d("extension check : ", mimeType);
            //RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
            String fileName = file.getName();
            mediafiles.add(prepareFilePart("mediafiles", file));
        }

        // Create other request parts
        RequestBody reqUserId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(userId));
        RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionText);
        RequestBody reqInterestId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(interestId));

        // Make the API call
        apiService.submitPost(userId, descriptionText, mediafiles, interestId)
                .enqueue(new Callback<AddPostRes>() {
                    @Override
                    public void onResponse(Call<AddPostRes> call, Response<AddPostRes> response) {

                        if (response.isSuccessful()) {
                            AddPostRes postResponse = response.body();
                            if (postResponse.isSuccess()) {
                                Toast.makeText(getApplicationContext(), postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to add post", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to communicate with the server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPostRes> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    // Handle the response
                });

}

    private void adjustRecyclerViewHeight() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_Gallery_Images);

        // Calculate the desired height based on your logic
        int desiredHeight = 400; // You need to implement this method

        // Set the height programmatically
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = desiredHeight;
        recyclerView.setLayoutParams(params);
    }

    // Function to find interest ID by name
    private long findInterestIdByName(String interestName) {
        for (Interest interest : interestList) {
            if (interest.getInterest_name().equals(interestName)) {
                return interest.getId();
            }
        }
        return -1; // Return -1 if not found (handle this case accordingly)
    }

    private MultipartBody.Part prepareFilePart(String partName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }

}// class end