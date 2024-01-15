package com.example.artcon_test.ui.portfolioPost;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.artcon_test.R;
import com.example.artcon_test.model.LikeRequest;
import com.example.artcon_test.model.LikeRes;
import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.network.PortfolioPostService;
import com.example.artcon_test.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioPostFragment extends Fragment {

    SharedPreferences sharedPreferences;

    PortfolioPostService portfolioPostService;
    boolean checkIfLiked = false;

    int intUser_id;

    public PortfolioPostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_portfolio_post, container, false);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfileViewModel profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.getSelectedPortfolioPost().observe(getViewLifecycleOwner(), selectedPost -> {
        if (selectedPost != null){
            Log.d("hatsuneMiku", "selected portfolio post: " + selectedPost.toString());
            ImageView portfolioImage = view.findViewById(R.id.portfolioImage);
            TextView portfolioTitle = view.findViewById(R.id.portfolioTitle);
            TextView portfolioDescription = view.findViewById(R.id.portfolioDescription);
            TextView username = view.findViewById(R.id.username);
            TextView fullName = view.findViewById(R.id.fullName);
            ImageView pfp = view.findViewById(R.id.pfp);
            Picasso.get()
                    .load(selectedPost.getMedia())
                    .placeholder(R.drawable.picasso_placeholder)
                    .into(portfolioImage);
            portfolioTitle.setText(selectedPost.getTitle());
            portfolioDescription.setText(selectedPost.getCaption());
            username.setText("@"+selectedPost.getUser().getUsername());
            fullName.setText(selectedPost.getUser().getFirstname() + " " + selectedPost.getUser().getLastname());
            Picasso.get()
                    .load(selectedPost.getUser().getPicture())
                    .placeholder(R.drawable.picasso_placeholder)
                    .into(pfp);

            //=========================================================
            ImageButton likeButton = view.findViewById(R.id.likeButton);
            sharedPreferences = getActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);

            String user_id = sharedPreferences.getString("userId", null);
            intUser_id = Integer.parseInt(user_id);
            int intPostId = selectedPost.getId();

            Call<Boolean> call1 = portfolioPostService.hasUserLikedPortfolioPost(intUser_id ,intPostId);

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
                        portfolioPostService.portfolioPostDislike(dislikeRequest)
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

                                                portfolioPostService.getPortfolio(String.valueOf(selectedPost.getId()))
                                                        .enqueue((new Callback<PortfolioPost>() {
                                                            @Override
                                                            public void onResponse(Call<PortfolioPost> call, Response<PortfolioPost> response) {
                                                                Log.d("like", "count");
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    PortfolioPost post = response.body();
                                                                    TextView likes = view.findViewById(R.id.likeCount);
                                                                    likes.setText(String.valueOf(post.getLikes()));
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PortfolioPost> call, Throwable t) {

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
                        likeRequest.setPost_id(Integer.valueOf(selectedPost.getId()));
                        //like
                        portfolioPostService.portfolioPostLike(likeRequest)
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
                                                portfolioPostService.getPortfolio(String.valueOf(selectedPost.getId()))
                                                        .enqueue((new Callback<PortfolioPost>() {
                                                            @Override
                                                            public void onResponse(Call<PortfolioPost> call, Response<PortfolioPost> response) {
                                                                Log.d("like", "count");
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    PortfolioPost post = response.body();

                                                                    TextView likes = view.findViewById(R.id.likeCount);
                                                                    likes.setText(String.valueOf(post.getLikes()));
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PortfolioPost> call, Throwable t) {

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


            //================================================================
        }});


    }
}