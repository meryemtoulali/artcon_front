package com.example.artcon_test;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Uri> uriArrayList;

    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_VIDEO = 2;

    public RecyclerAdapter(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate your item layout based on the viewType
        Log.d("upload","go to adapter class");
        Log.d("upload","this is viewType" + viewType);
        View view;
        if (viewType == VIEW_TYPE_IMAGE) {
            Log.d("upload","VIEW_TYPE_IMAGE");
            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selected_image, parent, false);
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.single_selected_image,parent,false);
        }else if (viewType == VIEW_TYPE_VIDEO) {
            Log.d("upload", "VIEW_TYPE_VIDEO");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selected_video, parent, false);
        }  else {
            Log.d("upload","VIEW_TYPE_else");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selected_video, parent, false);
        }
        return new ViewHolder(view);
       /* LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_selected_image,parent,false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.imageView.setImageURI(uriArrayList.get(position));

        Uri mediaUri = uriArrayList.get(position);

        // Load and display media using Glide
        if (getItemViewType(position) == VIEW_TYPE_IMAGE) {
            // Load and display image
            Glide.with(holder.itemView)
                    .load(mediaUri)
                    .into(holder.imageView);
        } else {
            // Load and display video thumbnail
            Glide.with(holder.itemView)
                    .load(getVideoThumbnailUri(mediaUri))
                    .into(holder.imageView);
        }

        // Set click listener or other operations
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriArrayList.remove(uriArrayList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }



    @Override
    public int getItemViewType(int position) {
        Uri mediaUri = uriArrayList.get(position);
        // Implement your logic to determine the view type based on the media type (image or video)
        if (isVideo(mediaUri)) {
            return VIEW_TYPE_VIDEO;
        } else {
            return VIEW_TYPE_IMAGE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    // Helper method to get video thumbnail URI
    private Uri getVideoThumbnailUri(Uri videoUri) {
        // Implement logic to get the video thumbnail URI using Glide or other methods
        // Return a valid Uri or null if not available
        return null;
    }
    private boolean isVideo(Uri mediaUri) {
        // Implement your logic to determine if the URI represents a video
        // For example, check the file extension or any other criteria
        // Return true if it's a video, false otherwise
        // Replace this with your actual logic
        return mediaUri.toString().toLowerCase().endsWith(".mp4");
    }
}

