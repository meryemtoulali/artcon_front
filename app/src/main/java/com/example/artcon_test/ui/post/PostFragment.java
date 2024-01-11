package com.example.artcon_test.ui.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.model.User;
import com.example.artcon_test.network.PostService;
import com.example.artcon_test.retrofit.RetrofitService;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;


public class PostFragment extends Fragment {

    private boolean hasImage = true;

    // Add a constant for the argument key
    private static final String ARG_POST_ID = "postId";

    public PostFragment() {
        // Required empty public constructor
    }

    // Method to create a new instance of PostFragment and pass the postId as an argument
    public static PostFragment newInstance(String postId) {
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);

        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //=======================================
        // Access the arguments and update the UI accordingly
        String postId = getArguments().getString(ARG_POST_ID);
        //Log.d("postfrag",postId);  G
        RetrofitService retrofitService = new RetrofitService();
        PostService postService = retrofitService.getRetrofit().create(PostService.class);

        Call<Post> call = postService.getPostById(postId);
        call.enqueue(new Callback<Post>(){

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();

                    User postUser = post.getUser();
                    String postFirstname = postUser.getFirstname();
                    String postLastname = postUser.getLastname();
                    String postUsername = postUser.getUsername();
                    String postUserPicture = postUser.getPicture();

                    List<MultipartBody.Part> mediaFiles = post.getMediaFiles();
                    String postDesc = post.getDescription();
                    Integer postLikes = post.getLikes();
                    //comments count


                    //
                    TextView fullName = view.findViewById(R.id.profileName);
                    String firstLastName = postFirstname +" "+ postLastname;
                    fullName.setText(firstLastName);

                    TextView userName = view.findViewById(R.id.username);
                    String postAtUsername = "@"+ postUsername;
                    userName.setText(postAtUsername);

                    ImageView profilePic = view.findViewById(R.id.profileImage);
                    // Check if the URL is not null or empty before using Picasso
                    if (postUserPicture != null && !postUserPicture.isEmpty()) {
                        Picasso.get()
                                .load(postUserPicture)
                                .placeholder(R.drawable.profile_picture_placeholder)
                                .into(profilePic);
                    } else {
                        // Handle the case where the URL is null or empty
                        // You might want to set a default image or take other appropriate action
                        profilePic.setImageResource(R.drawable.default_pfp);
                    }

                    TextView desc = view.findViewById(R.id.postTextArea);
                    desc.setText(postDesc);

                    //TextView likes = view.findViewById(R.id.likeCount);
                    //likes.setText(postLikes);
                }else {
                    // Handle error
                    Log.d("Interest", "response is not succeed");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        //===============================================================================

        ImageView postImage = view.findViewById(R.id.postImage);
        if (!hasImage) {
            // If the post doesn't have an image, hide the ImageView
            postImage.setVisibility(View.GONE);
        }
        return view;
    }
}

