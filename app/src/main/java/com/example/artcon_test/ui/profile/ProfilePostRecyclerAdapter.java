package com.example.artcon_test.ui.profile;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.ui.post.PostFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePostRecyclerAdapter extends RecyclerView.Adapter<ProfilePostRecyclerAdapter.PostViewHolder> {
    private List<Post> postList;
    private FrameLayout postContainer;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile_post_item, parent, false);
        postContainer = itemView.findViewById(R.id.postContainer);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        if(postList != null) return postList.size();
        else return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        // Declare your views here
        FrameLayout container;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.postContainer);
        }

        public void bind(Post post) {
            // Pass the post ID to PostFragment
            String postId = post.getId().toString();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.postContainer, PostFragment.newInstance(postId));
            fragmentTransaction.commit();
        }


    }


}
