package com.example.artcon_test.ui.portfolioPost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artcon_test.R;
import com.example.artcon_test.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class PortfolioPostFragment extends Fragment {

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
        }});


    }
}