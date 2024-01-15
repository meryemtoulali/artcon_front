package com.example.artcon_test.ui.post;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.AddPostRes;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.network.PostService;
import com.example.artcon_test.retrofit.RetrofitService;
import com.example.artcon_test.ui.RealPathUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostFragment extends Fragment {

    private RecyclerView recyclerView;

    private ArrayList<Uri> uri = new ArrayList<>();
    private RecyclerAdapter adapterImages;

    private static final int Read_Permission = 101;

    private AutoCompleteTextView textCategory;

    private String category;

    private List<Interest> interestList;

    private TextInputEditText PostDescription;
    //TextInputEditText postEditText;
    private ImageView imagePost;
    //ImageView docPost;
    private String descriptionText;
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_VIDEO = 2;

    private long selectedInterestId;
    private Uri selectedImageUri;
    private String path;

    private VideoView videoPost;
    // constant to compare
    // the activity result code
    private final int SELECT_PICTURE = 200;
    private final int SELECT_VIDEO = 200;
    private final int SELECT_DOC =200;

    SharedPreferences sharedPreferences;
    ViewGroup view;

    public AddPostFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = (ViewGroup) inflater.inflate(R.layout.fragment_add_post, container, false);

        Log.d("hi", "ok");

        textCategory = view.findViewById(R.id.textCategory);


        //postEditText = findViewById(R.id.textPostDescription);
        imagePost = view.findViewById(R.id.imageView);
        ImageView videoPost = view.findViewById(R.id.videoView);

        RetrofitService retrofitService = new RetrofitService();
        PostService postService = retrofitService.getRetrofit().create(PostService.class);

        Call<List<Interest>> call = postService.getAllInterests();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, interestNames);
                    AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.textCategory);
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

                //jadid
                // Get the selected text
                String selectedInterestName = parent.getItemAtPosition(position).toString();

                Log.d("category", "category name is : "+ selectedInterestName);
                // Find the corresponding interest ID
                selectedInterestId = findInterestIdByName(selectedInterestName);

                Log.d("category", "category id is : "+ selectedInterestId);
            }
        });

        PostDescription = view.findViewById(R.id.textPostDescription);
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


        recyclerView = view.findViewById(R.id.recyclerView_Gallery_Images);

        adapterImages = new RecyclerAdapter(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapterImages);

        //Permission
        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("image", "on click");
/*working
                // Vérification des autorisations
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    Log.d("image", "no permission");
                    // Demander la permission si elle n'est pas accordée
                    //  requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                   //         Read_Permission);

                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            Read_Permission);


                } else {
                    Log.d("image", "start image chooser");
                         */
                imageChooser();
                // }
                adjustRecyclerViewHeight();
            }
        });


        videoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.d("vedio", "no permission");
                    // Demander la permission si elle n'est pas accordée
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            Read_Permission);
                } else {*/
                    videoChooser();
               // }
                adjustRecyclerViewHeight();
            }
        });

        // add post click
        Button btnAddPost = view.findViewById(R.id.buttonAddPost);
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Long interest_id = selectedInterestId;
                String description = descriptionText;
                //userId
                sharedPreferences = getActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);

                String user_id = sharedPreferences.getString("userId", null);
                Log.d("aha", "id : "+ selectedInterestId);
                Log.d("aha", "description: "+ descriptionText);
                int intUser_id = Integer.parseInt(user_id);
                Log.d("ahaa", "userid : "+ intUser_id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    addPost(intUser_id, descriptionText, uri, interest_id);
                }
            }
        });

        //Cancel
        Button btnCancelPost = view.findViewById(R.id.buttonCancelPost);
        btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelConfirmationDialog();
            }
        });
        //Back btn
        ImageView btnBack = view.findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelConfirmationDialog();
            }
        });

        ///
        return view;
    }//oncreate end


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
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterImages.notifyDataSetChanged();
                    }
                });*/
                adapterImages.notifyDataSetChanged();
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
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterImages.notifyDataSetChanged();
                    }
                });*/
                adapterImages.notifyDataSetChanged();
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
        PostService postService = retrofitService.getRetrofit().create(PostService.class);

        // Prepare the request body
        List<MultipartBody.Part> mediafiles = new ArrayList<>();
        for (Uri uri : imageUris) {
            File file = new File(RealPathUtil.getRealPath(getActivity(), uri));
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
        postService.submitPost(userId, descriptionText, mediafiles, interestId)
                .enqueue(new Callback<AddPostRes>() {
                    @Override
                    public void onResponse(Call<AddPostRes> call, Response<AddPostRes> response) {

                        if (response.isSuccessful()) {
                            AddPostRes postResponse = response.body();
                            if (postResponse.isSuccess()) {
                                Integer postId = postResponse.getPostId();
                                Log.d("id", String.valueOf(postId));
                                Toast.makeText(getActivity(), postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                //
                                Bundle result = new Bundle();
                                result.putString("postId", String.valueOf(postId));

                                //navigateToFragment(R.id.action_global_navigation_view_post);
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.action_global_navigation_view_post, result);

                                /*Bundle result = new Bundle();
                                result.putString("postId", String.valueOf(postId));
                                getParentFragmentManager().setFragmentResult("postIdFromAnotherFrag", result);
                                //action_navigation_add_post_to_navigation_view_post
                                //navigateToFragment(R.id.action_navigation_add_post_to_navigation_home2); good
                                navigateToFragment(R.id.action_global_navigation_view_post);*/
                            } else {
                                Toast.makeText(getActivity(), "Failed to add post", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Failed to communicate with the server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPostRes> call, Throwable t) {

                        Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    // Handle the response
                });

    }

    private void adjustRecyclerViewHeight() {
        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView_Gallery_Images);

        // Calculate the desired height based on your logic
        int desiredHeight = 300; // You need to implement this method

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

    private void navigateToFragment(int fragmentId) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(fragmentId);
    }

    // Method to show the confirmation dialog
    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Abandon the post");
        builder.setMessage("Are you sure you want to cancel the post?");

        // Add the positive button (OK)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigateToFragment(R.id.action_navigation_add_post_to_navigation_home2);
                // User confirmed, you can perform any action here or just close the dialog
                dialog.dismiss();
            }
        });

        // Add the negative button (Cancel)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled, close the dialog
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}