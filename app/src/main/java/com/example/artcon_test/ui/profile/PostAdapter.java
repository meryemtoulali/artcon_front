package com.example.artcon_test.ui.profile;


import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.example.artcon_test.ui.post.MediaAdapter;
import com.example.artcon_test.ui.search.PeopleAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private FrameLayout postContainer;


    public List<Post> getPostList() {
        return postList;
    }
    private final SharedPreferences sharedPreferences;


    public PostAdapter(Context context) {
        this.sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
    }
    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post, parent, false);
        return new PostViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(postList.get(position));
        holder.itemView.findViewById(R.id.userArea).setOnClickListener(v -> {
            String selectedUserId = postList.get(position).getUser().getId().toString();
            onUserAreaClick(selectedUserId);
        });

    }

    public interface OnUserAreaClickListener {
        void onUserAreaClick(String userId);
    }

    private OnUserAreaClickListener userAreaClickListener;
    // Setter for the listener
    public void setOnUserAreaClickListener(OnUserAreaClickListener listener) {
        this.userAreaClickListener = listener;
    }

    // Method to handle item click and pass the user ID
    private void onUserAreaClick(String userId) {
        if (userAreaClickListener != null) {
            userAreaClickListener.onUserAreaClick(userId);
        }
    }


    @Override
    public int getItemCount() {
        if(postList != null) return postList.size();
        else return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        // Declare your views here
        private final View itemView;
        private final TextView fullName;
        private final TextView userName;
        private final ImageView profilePic;
        private final TextView desc;
        private final TextView likes;
        private final ViewPager2 viewPager2;
        private final ImageButton likeButton;
        private final CardView cardView;
        private MediaAdapter mediaAdapter;
        boolean checkIfLiked = false;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            fullName = itemView.findViewById(R.id.profileName);
            userName = itemView.findViewById(R.id.username);
            profilePic = itemView.findViewById(R.id.profileImage);
            desc = itemView.findViewById(R.id.postTextArea);
            likes = itemView.findViewById(R.id.likeCount);
            viewPager2 = itemView.findViewById(R.id.mediaViewPager);
            likeButton = itemView.findViewById(R.id.likeButton);
            cardView = itemView.findViewById(R.id.postImageCardView);
        }

        public void bind(Post post) {
            // Populate views with data from the Post object
            User postUser = post.getUser();
            String postFirstname = postUser.getFirstname();
            String postLastname = postUser.getLastname();
            String postUsername = postUser.getUsername();
            String postUserPicture = postUser.getPicture();

            List<MediaItem> mediaFiles = post.getMediaFiles();
            String postDesc = post.getDescription();
            Integer postLikes = post.getLikes();
            // fill view with data
            fullName.setText(postFirstname +" "+ postLastname);
            String postAtUsername = "@"+ postUsername;
            userName.setText(postAtUsername);

            // Load profile picture with Picasso (similar to how you did in the fragment)
            if (postUserPicture != null && !postUserPicture.isEmpty()) {
                Picasso.get()
                        .load(postUserPicture)
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(profilePic);
            } else {
                profilePic.setImageResource(R.drawable.default_pfp);
            }
            postDesc = postDesc.replaceAll("^\"|\"$|\n", "").trim();
            postDesc = postDesc.replace("\\n", "\n");

            desc.setText(postDesc);
            likes.setText(String.valueOf(post.getLikes()));

            // Set up the ViewPager2 with media files

            if(mediaFiles.isEmpty()){
                //is emplty
                cardView.setVisibility(GONE);
            }else {
                mediaAdapter = new MediaAdapter(mediaFiles);
                viewPager2.setAdapter(mediaAdapter);
                viewPager2.setClipChildren(false);
                viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            }

            String user_id = sharedPreferences.getString("userId", null);
            int intUser_id = Integer.parseInt(user_id);
            int intPostId = post.getId();
            RetrofitService retrofitService = new RetrofitService();
            PostService postService = retrofitService.getRetrofit().create(PostService.class);

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

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("like", "on click");

                    if (checkIfLiked){
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
                                                postService.getLikeCount(post.getId().toString())
                                                        .enqueue((new Callback<Integer>() {
                                                            @Override
                                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                Log.d("like", "count");
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    Integer likeCount = response.body();
                                                                    Log.d("like", "Like Count : " + likeCount);
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
                                                postService.getLikeCount(post.getId().toString())
                                                        .enqueue((new Callback<Integer>() {
                                                            @Override
                                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                Log.d("like", "count");
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    Integer likeCount = response.body();
                                                                    Log.d("like", "Like Count : " + likeCount);
                                                                    likes.setText(String.valueOf(likeCount));
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Integer> call, Throwable t) {

                                                            }
                                                        } ));

                                            }else {
                                                Log.e("like", "failed to like post");

                                            }
                                        } else {
                                            // Handle the case where the response is not successful or the body is null
                                            Log.d("like", "the response is not successful");
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<LikeRes> call, Throwable t) {
                                        Log.e("like", "failed to like post");
                                    }
                                }));

                    }

                }
            });

        }

    }


}