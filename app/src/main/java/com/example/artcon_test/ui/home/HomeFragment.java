package com.example.artcon_test.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentHomeBinding;
import com.example.artcon_test.ui.profile.PostAdapter;
import com.example.artcon_test.ui.search.PeopleAdapter;

public class HomeFragment extends Fragment implements PostAdapter.OnUserAreaClickListener {

    private String TAG = "HOME FRAGMENT";
    private String userId;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    private PostAdapter postAdapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(getContext());
        postAdapter.setOnUserAreaClickListener(this);

        recyclerView.setAdapter(postAdapter);
        Log.d(TAG, "created view recycler: " + recyclerView.toString());
        homeViewModel.getHome(userId);
        homeViewModel.getHomeLiveData().observe(getViewLifecycleOwner(), postList -> {
            // Update recycler adapter with post list data
            postAdapter.setPostList(postList);
            postAdapter.notifyDataSetChanged();
        });
        return root;
    }

    @Override
    public void onUserAreaClick(String userId) {
        Bundle args = new Bundle();
        args.putString("selectedUserId", userId);
        Navigation.findNavController(requireView()).navigate(R.id.action_home_to_profile, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}