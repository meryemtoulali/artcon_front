package com.example.artcon_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class ProfilePortfolioFragment extends Fragment {
    private GridView gridView;
    private ProfilePortfolioGridAdapter gridAdapter;


    public ProfilePortfolioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);

        int[] portfolioImages = {R.drawable.pfp1, R.drawable.image_22, R.drawable.image_22,
                R.drawable.image_22, R.drawable.image_22, R.drawable.image_22,};

        gridView = view.findViewById(R.id.PortfolioGridView);
        gridAdapter = new ProfilePortfolioGridAdapter(getActivity(), portfolioImages);
        gridView.setAdapter(gridAdapter);

        return view;
    }
}