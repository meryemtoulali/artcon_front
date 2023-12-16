package com.example.artcon_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfilePortfolioFragment extends Fragment {
    private String USER_ID="1";
    private GridView gridView;
    private ProfilePortfolioGridAdapter gridAdapter;
    private ProfileViewModel viewModel;

    public ProfilePortfolioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

//        int[] portfolioImages = {R.drawable.image_22, R.drawable.image_22, R.drawable.image_22,
//                R.drawable.image_22, R.drawable.image_22, R.drawable.image_22,};
        gridView = view.findViewById(R.id.PortfolioGridView);

        viewModel.getPortfolioLiveData().observe(getViewLifecycleOwner(), portfolio -> {
            // Update gridAdapter with posts data
            if (gridAdapter == null) {
                gridAdapter = new ProfilePortfolioGridAdapter(requireContext(), portfolio);
                gridView.setAdapter(gridAdapter);
            } else {
                gridAdapter.setPortfolio(portfolio);
                gridAdapter.notifyDataSetChanged();
            }
        });


        viewModel.getPortfolio(USER_ID);
        //TODO: add user id from profile activity

//
//
//        gridView = view.findViewById(R.id.PortfolioGridView);
//        gridAdapter = new ProfilePortfolioGridAdapter(getActivity(), portfolioImages);
//        gridView.setAdapter(gridAdapter);

        return view;
    }
}