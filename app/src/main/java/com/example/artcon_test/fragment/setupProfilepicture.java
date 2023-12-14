package com.example.artcon_test.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.UpdateUserViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setupProfilepicture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setupProfilepicture extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int SELECT_PICTURE = 200;
    UpdateUserViewModel user;

    ImageView pdp;

    public setupProfilepicture() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setupProfilepicture.
     */
    // TODO: Rename and change types and number of parameters
    public static setupProfilepicture newInstance(String param1, String param2) {
        setupProfilepicture fragment = new setupProfilepicture();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_profilepicture, container, false);
        Button addPhoto = view.findViewById(R.id.addPhoto);
        pdp = view.findViewById(R.id.photo);
        user = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);

        addPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage();
                    }
                }
        );
        return view;
    }

    private void chooseImage()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(galleryIntent);

    }

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // Handle the selected image URI
                    Uri selectedImageUri = result.getData().getData();
                    // Now you can use the selectedImageUri to do whatever you need

                    pdp.setImageURI(selectedImageUri);
                    Log.d("File test URI", String.valueOf(selectedImageUri));
                    File selectedImageFile = uriToFile(selectedImageUri,getContext());
                    Log.d("File test", String.valueOf(selectedImageFile));
//                    MultipartBody.Part picturePart = prepareFilePart("picture", selectedImageFile);
                    user.setPicture(selectedImageFile);

                }
            }
    );

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
}