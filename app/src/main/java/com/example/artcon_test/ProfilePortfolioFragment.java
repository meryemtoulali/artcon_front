package com.example.artcon_test;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.artcon_test.model.PortfolioPost;
import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfilePortfolioFragment extends Fragment implements ProfilePortfolioGridAdapter.OnGridItemClickListener {
    private OnPostClickListener onPostClickListener;
    private String TAG = "hatsunemiku";
    private static final String ARG_USER_ID = "userId";
    private ProfileViewModel profileViewModel;
    private String userId;

    private GridView gridView;
    private ProfilePortfolioGridAdapter gridAdapter;
    public ProfilePortfolioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onPostClickListener = (OnPostClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPostClickListener");
        }
    }
    public static ProfilePortfolioFragment newInstance(String userId) {
        ProfilePortfolioFragment fragment = new ProfilePortfolioFragment();
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
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        Log.d(TAG, "viewModel created in portfolio fragment: " + profileViewModel);
//        profileViewModel.getUserById(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);
//        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

//        int[] portfolioImages = {R.drawable.image_22, R.drawable.image_22, R.drawable.image_22,
//                R.drawable.image_22, R.drawable.image_22, R.drawable.image_22,};
        gridView = view.findViewById(R.id.PortfolioGridView);

        profileViewModel.getPortfolioLiveData().observe(getViewLifecycleOwner(), portfolio -> {
            // Update gridAdapter with posts data
            if (gridAdapter == null) {
                gridAdapter = new ProfilePortfolioGridAdapter(requireContext(), portfolio, this);
                gridView.setAdapter(gridAdapter);
            } else {
                gridAdapter.setPortfolio(portfolio);
                gridAdapter.notifyDataSetChanged();
            }
        });


        profileViewModel.getPortfolio(userId);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        //grid adapter item click
        Log.d(TAG, "grid item clicked");
        PortfolioPost selectedPost = (PortfolioPost) gridAdapter.getItem(position);

        Log.d(TAG, "selected post in grid adap: " + selectedPost);
        profileViewModel.setSelectedPortfolioPost(selectedPost);
        handlePostClick();
    }
    private void handlePostClick() {
        //replace profile fragment with portfoliopost fragment
        Log.d(TAG, "in replace fragment function");

        if (getActivity() instanceof OnPostClickListener) {
            onPostClickListener.onPortfolioPostClick();
        }
    }


}