package com.example.artcon_test.ui.post;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.MediaItem;

import android.widget.ImageView;
import android.widget.VideoView;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<MediaItem> mediaList;
    private VideoView currentVideoView;

    public MediaAdapter(List<MediaItem> mediaList) {
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaItem mediaItem = mediaList.get(position);

        boolean foundImage = false;
        String url = mediaItem.getMediafile_url();
        String[] searchImgs = {"jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp", "svg"};
        for (String searchImg : searchImgs) {
            if (url.contains(searchImg)) {
                System.out.println("The img contains the string: " + searchImg);
                foundImage = true;
                // If you want to stop the loop after finding the first match, you can break here:
                Log.d("media found", "ok image ");

                // Display image
                holder.imageView.setVisibility(View.VISIBLE);
                holder.videoView.setVisibility(View.GONE);
                holder.imageView.setImageURI(Uri.parse(mediaItem.getMediafile_url()));
                break;
            } else {
                System.out.println("The img does not contain the string: " + searchImg);
            }
        }

        if (!foundImage) {
            String[] searchVideos = {"mp4", "avi", "mkv", "wmv", "webm"};
            for (String searchVideo : searchVideos) {
                if (url.contains(searchVideo)) {
                    System.out.println("The video contains the string: " + searchVideo);
                    // If you want to stop the loop after finding the first match, you can break here:
                    Log.d("media found", "ok video ");

                    // Display video
                    holder.imageView.setVisibility(View.GONE);
                    holder.videoView.setVisibility(View.VISIBLE);

                    // Use Picasso for video thumbnail or load the video directly (based on your requirements)
                    // Picasso.get().load(videoThumbnailUrl).into(holder.imageView);
                    holder.videoView.setVideoURI(Uri.parse(mediaItem.getMediafile_url()));
                    holder.videoView.start();

                    // Set up OnPreparedListener to start playback when prepared
                    holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // Set looping for the video
                            mp.setLooping(true);

                            // Start playback once prepared
                            holder.videoView.start();
                        }
                    });


                    break;
                } else {
                    System.out.println("The videpo does not contain the string: " + searchVideo);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // Set looping for the video
                    mp.setLooping(true);
                }
            });
            // Add click listener to VideoView to toggle play/pause
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleVideoPlayback();
                }
            });

        }

        // Method to toggle video playback
        private void toggleVideoPlayback() {
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
        }
    }
}
