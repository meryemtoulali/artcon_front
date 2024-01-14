package com.example.artcon_test.ui.home;

import static android.content.Context.MODE_PRIVATE;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.chat.UsersActivity;
import com.example.artcon_test.chat.UsersFirstActivity;
import com.example.artcon_test.databinding.FragmentHomeBinding;
import com.example.artcon_test.ui.profile.ProfilePostRecyclerAdapter;
import com.example.artcon_test.utilities.Constants;

public class HomeFragment extends Fragment {

    private String TAG = "HOME FRAGMENT";
    private String userId;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    private ProfilePostRecyclerAdapter postRecyclerAdapter;

    //On create method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        userId=preferences.getString("userId",null);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.feed;

        binding.chat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UsersFirstActivity.class);
            getActivity().startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        postRecyclerAdapter = new ProfilePostRecyclerAdapter(getContext());
        recyclerView.setAdapter(postRecyclerAdapter);

        homeViewModel.getHome(userId);
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