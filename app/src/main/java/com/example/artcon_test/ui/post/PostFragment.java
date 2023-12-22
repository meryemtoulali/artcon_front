package com.example.artcon_test.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.artcon_test.R;

public class PostFragment extends Fragment {

    private boolean hasImage = true;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        ImageView postImage = view.findViewById(R.id.postImage);
        if (!hasImage) {
            // If the post doesn't have an image, hide the ImageView
            postImage.setVisibility(View.GONE);
        }
        return view;
    }
}

