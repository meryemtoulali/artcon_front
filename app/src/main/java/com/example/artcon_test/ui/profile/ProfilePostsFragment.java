package com.example.artcon_test.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artcon_test.R;
import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfilePostsFragment extends Fragment {

    private static final String ARG_USER_ID = "userId";
    private final String TAG="hatsunemiku";
    private ProfileViewModel profileViewModel;

    private String userId;

    public ProfilePostsFragment() {
        // Required empty public constructor
    }

    public static ProfilePostsFragment newInstance(String userId) {
        ProfilePostsFragment fragment = new ProfilePostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Log.d(TAG, "viewModel created in posts fragment: " + profileViewModel);
        profileViewModel.getUserById(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_posts, container, false);
    }
}