package com.example.artcon_test.ui.post;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.artcon_test.R;
import com.example.artcon_test.model.LikeRequest;
import com.example.artcon_test.model.LikeRes;
import com.example.artcon_test.model.MediaItem;
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

    private RecyclerView recyclerView;
    private ViewPager2 viewPager2;
    private MediaAdapter mediaAdapter;
    private boolean hasImage = true;

    // Add a constant for the argument key
    private static final String ARG_POST_ID = "postId";
    SharedPreferences sharedPreferences;

    boolean checkIfLiked = false;

    public PostFragment() {
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
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();

                    User postUser = post.getUser();
                    String postFirstname = postUser.getFirstname();
                    String postLastname = postUser.getLastname();
                    String postUsername = postUser.getUsername();
                    String postUserPicture = postUser.getPicture();

                    List<MediaItem> mediaFiles = post.getMediaFiles();
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
                        profilePic.setImageResource(R.drawable.default_pfp);
                    }

                    TextView desc = view.findViewById(R.id.postTextArea);
                    postDesc = postDesc.replaceAll("^\"|\"$|\n", "").trim();
                    postDesc = postDesc.replace("\\n", "\n");
                    desc.setText(postDesc);

                    TextView likes = view.findViewById(R.id.likeCount);
                    likes.setText(String.valueOf(postLikes));

                    viewPager2 = view.findViewById(R.id.mediaViewPager);
//mediaFiles
                    // Create an instance of the MediaAdapter and set it as the adapter for the RecyclerView
                    if(mediaFiles.isEmpty()){
                        //is emplty
                        CardView cardView = view.findViewById(R.id.postImageCardView);
                        cardView.setVisibility(view.GONE);
                    }else {
                        mediaAdapter = new MediaAdapter(mediaFiles);
                        viewPager2.setAdapter(mediaAdapter);

                        //ViewPager2.setClipToPadding(false);
                        viewPager2.setClipChildren(false);
                        //ViewPager2.setOffscreenPageLimit(2);
                        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                    }

                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
        //CHECK IF USER HAS ALREADY LIKED THE POST
        ImageButton likeButton = view.findViewById(R.id.likeButton);
        sharedPreferences = getActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);

        String user_id = sharedPreferences.getString("userId", null);
        int intUser_id = Integer.parseInt(user_id);
        int intPostId = Integer.parseInt(postId);

        Call<Boolean> call1 = postService.hasUserLikedPost(intUser_id ,intPostId);

        call1.enqueue((new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean res = response.body();
                    Log.d("like","succeed : " + res);
                    if (res != null) {
                        if (res) {
                            // User has already liked the post
                            checkIfLiked = res;
                            likeButton.setImageResource(R.drawable.like_led);
                        } else {
                            // User has not liked the post
                        }
                    } else {
                        // Handle null response body
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        }));

        //
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("like", "on click");
                //
                if (checkIfLiked){
                    //Toast.makeText(getActivity(), "thisis dislike section", Toast.LENGTH_SHORT).show();
                    Log.d("like","liked");
                    LikeRequest dislikeRequest = new LikeRequest();
                    dislikeRequest.setUser_id(intUser_id);
                    dislikeRequest.setPost_id(intPostId);
                    //dislike methode
                    postService.dislikePost(dislikeRequest)
                            .enqueue((new Callback<LikeRes>() {
                                @Override
                                public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {
                                    Log.d("like","on response dislike");
                                    if (response.isSuccessful() && response.body() != null) {
                                        LikeRes dislikeRes = response.body();
                                        if(dislikeRes.isSuccess()){
                                            Log.d("like","about to change the icon to dislike");
                                            checkIfLiked = false;
                                            likeButton.setImageResource(R.drawable.ic_like);
                                            //i have to change the like number again after like
                                            postService.getLikeCount(postId)
                                                    .enqueue((new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            Log.d("like", "count");
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                Integer likeCount = response.body();
                                                                Log.d("like", "Like Count : " + likeCount);
                                                                TextView likes = view.findViewById(R.id.likeCount);
                                                                likes.setText(String.valueOf(likeCount));
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Integer> call, Throwable t) {

                                                        }
                                                    } ));

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LikeRes> call, Throwable t) {

                                }
                            }));
                }else{
                    Log.d("like","not liked");
                    LikeRequest likeRequest = new LikeRequest();
                    likeRequest.setUser_id(intUser_id);
                    likeRequest.setPost_id(intPostId);
                    //like
                    postService.likePost(likeRequest)
                            .enqueue((new Callback<LikeRes>() {
                                @Override
                                public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {
                                    Log.d("like","on response");
                                    if (response.isSuccessful() && response.body() != null) {
                                        LikeRes likeRes = response.body();
                                        if(likeRes.isSuccess()){
                                            Log.d("like","about to change the icon");
                                            checkIfLiked = true;
                                            likeButton.setImageResource(R.drawable.like_led);
                                            //i have to change the like number again after like
                                            postService.getLikeCount(postId)
                                                    .enqueue((new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            Log.d("like", "count");
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                Integer likeCount = response.body();
                                                                Log.d("like", "Like Count : " + likeCount);
                                                                TextView likes = view.findViewById(R.id.likeCount);
                                                                likes.setText(String.valueOf(likeCount));
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Integer> call, Throwable t) {

                                                        }
                                                    } ));

                                        }else {
                                            Toast.makeText(getActivity(), "Failed to like post", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Handle the case where the response is not successful or the body is null
                                        Log.d("like", "the response is not successful");
                                    }

                                }

                                @Override
                                public void onFailure(Call<LikeRes> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Failed to like post 2 ", Toast.LENGTH_SHORT).show();
                                }
                            }));
                }

            }
        });
        return view;
    }
}