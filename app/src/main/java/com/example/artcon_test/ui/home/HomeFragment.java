package com.example.artcon_test.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.databinding.FragmentHomeBinding;
import com.example.artcon_test.ui.profile.ProfilePostRecyclerAdapter;

public class HomeFragment extends Fragment {

    private String TAG = "HOME FRAGMENT";

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ProfilePostRecyclerAdapter postRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.feed;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        postRecyclerAdapter = new ProfilePostRecyclerAdapter();
        recyclerView.setAdapter(postRecyclerAdapter);
        Log.d(TAG, "created view recycler: " + recyclerView.toString());
        homeViewModel.getHome();
        homeViewModel.getHomeLiveData().observe(getViewLifecycleOwner(), postList -> {
            // Update recycler adapter with post list data
            postRecyclerAdapter.setPostList(postList);
            postRecyclerAdapter.notifyDataSetChanged();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}