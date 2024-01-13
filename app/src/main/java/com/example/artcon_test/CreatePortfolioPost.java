package com.example.artcon_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreatePortfolioPost extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    private static final int SELECT_VIDEO = 201;
    private static final int SELECT_DOC = 202;

    private final ArrayList<Uri> uriList = new ArrayList<>();

    // Create separate ActivityResultLauncher instances for each type of operation
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        handleActivityResult(SELECT_PICTURE, data);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> photoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        handleActivityResult(SELECT_DOC, data);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> videoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        handleActivityResult(SELECT_VIDEO, data);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_portfolio_post);

        // Setting up button click listeners
        Button imageButton = findViewById(R.id.imagePhoto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here to open gallery for image selection
                openGallery();
            }
        });

        Button videoButton = findViewById(R.id.videoView);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Handle button click here to open the camera for video recording
                openCameraForVideo();
            }
        });

        Button photoButton = findViewById(R.id.buttonAddPhoto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here to open the camera for photo capture
                openCameraForPhoto();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        galleryLauncher.launch(galleryIntent);
    }

    private void openCameraForPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoLauncher.launch(takePictureIntent);
    }

    private void openCameraForVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoLauncher.launch(takeVideoIntent);
    }

    private void handleActivityResult(int requestCode, Intent data) {
        if (data != null) {
            if (requestCode == SELECT_PICTURE || requestCode == SELECT_VIDEO) {
                Uri selectedMediaUri = data.getData();
                // Perform further actions with the selected media URI
                // ...
            } else if (requestCode == SELECT_DOC) {
                // Handle document selection
            }
        }
    }

    private void restoreOriginalColorDelayed(final View view, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Restore the original background color after the delay
                view.setBackgroundResource(R.drawable.grey_border);
            }
        }, delayMillis);
    }
}
