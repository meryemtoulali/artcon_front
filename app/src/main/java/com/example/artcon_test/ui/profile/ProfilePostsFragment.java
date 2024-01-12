package com.example.artcon_test.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artcon_test.R;
import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfilePostsFragment extends Fragment {
    private String TAG = "hatsunemiku";
    private static final String ARG_USER_ID = "userId";
    private ProfileViewModel profileViewModel;
    private String userId;
    private ProfilePostRecyclerAdapter postRecyclerAdapter;


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
        Log.d(TAG, "creating posts fragment");
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
        View view = inflater.inflate(R.layout.fragment_profile_posts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.postRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        postRecyclerAdapter = new ProfilePostRecyclerAdapter();
        recyclerView.setAdapter(postRecyclerAdapter);
        Log.d(TAG, "created view recycler: " + recyclerView.toString());

        profileViewModel.getPostList(userId);
        profileViewModel.getPostListLiveData().observe(getViewLifecycleOwner(), postList -> {
            // Update recycler adapter with post list data
                postRecyclerAdapter.setPostList(postList);
                postRecyclerAdapter.notifyDataSetChanged();
            });
        return view;
    }
}