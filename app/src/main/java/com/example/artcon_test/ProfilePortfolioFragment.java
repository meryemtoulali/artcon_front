package com.example.artcon_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfilePortfolioFragment extends Fragment {
    private String TAG = "hatsunemiku";
    private static final String ARG_USER_ID = "userId";
    private ProfileViewModel profileViewModel;
    private String userId;

    private GridView gridView;
    private ProfilePortfolioGridAdapter gridAdapter;
    public ProfilePortfolioFragment() {
        // Required empty public constructor
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
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
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
                gridAdapter = new ProfilePortfolioGridAdapter(requireContext(), portfolio);
                gridView.setAdapter(gridAdapter);
            } else {
                gridAdapter.setPortfolio(portfolio);
                gridAdapter.notifyDataSetChanged();
            }
        });


        profileViewModel.getPortfolio(userId);

        return view;
    }
}