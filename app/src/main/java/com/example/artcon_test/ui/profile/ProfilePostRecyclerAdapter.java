package com.example.artcon_test.ui.profile;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePostRecyclerAdapter extends RecyclerView.Adapter<ProfilePostRecyclerAdapter.PostViewHolder> {
    private Context context;
    private List<Post> postList;

    public List<Post> getPostList() {
        return postList;
    }
    private static String TAG = "hatsunemiku";

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout and create a view holder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post, parent, false);
        return new PostViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Bind your data to the views in the view holder
        Post post = postList.get(position);
        holder.bind(post);
    }
    @Override
    public int getItemCount() {
        if(postList != null) return postList.size();
        else return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        // Declare your views here
        ImageView postImage;
        ImageView pfp;

        TextView username;
        TextView fullName;
        TextView likeCount;
        TextView commentCount;
        TextView postTextArea;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            postImage = itemView.findViewById(R.id.postImage);
            pfp = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.username);
            fullName = itemView.findViewById(R.id.profileName);
            postTextArea = itemView.findViewById(R.id.postTextArea);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);
        }

        public void bind(Post post) {
            // Bind the data to your views here
            if(post.getMediaFiles() == null || post.getMediaFiles().isEmpty()){
                postImage.setVisibility(View.GONE);
            } else {
                Picasso.get()
                        .load(post.getMediaFiles().get(0).getMediafile_url())
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(postImage);
            }
            if(post.getUser().getPicture() != null && !post.getUser().getPicture().isEmpty())
            Picasso.get()
                    .load(post.getUser().getPicture())
                    .placeholder(R.drawable.picasso_placeholder)
                    .into(pfp);
            username.setText(post.getUser().getUsername());
            fullName.setText(post.getUser().getFirstname() + " " + post.getUser().getLastname());
            postTextArea.setText(post.getDescription());
            likeCount.setText(String.valueOf(post.getLikes()));
//            commentCount.setText(post.getCommentCount());
        }
    }


}
