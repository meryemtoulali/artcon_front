package com.example.artcon_test.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentHomeBinding;
import com.example.artcon_test.ui.login.LoginActivity;
import com.example.artcon_test.ui.profile.ProfilePostRecyclerAdapter;
import com.example.artcon_test.viewmodel.LogoutViewModel;

public class HomeFragment extends Fragment {

    private String TAG = "HOME FRAGMENT";
    private String userId;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    LogoutViewModel logoutViewModel;
    HomeViewModel homeViewModel;
    private ProfilePostRecyclerAdapter postRecyclerAdapter;

    //On create method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        userId=preferences.getString("userId",null);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.feed;
        ImageView kebabMenu = root.findViewById(R.id.kebab_menu);

        kebabMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), v);
            popup.getMenuInflater().inflate(R.menu.kebab_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_logout) {
                    logoutViewModel.logout();
                    Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                    navigateToLoginPage();
                    clearUserSession();
                    return true;
                }
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                return false;
            });
            popup.show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        postRecyclerAdapter = new ProfilePostRecyclerAdapter(getContext());
        recyclerView.setAdapter(postRecyclerAdapter);
        Log.d(TAG, "created view recycler: " + recyclerView.toString());
        homeViewModel.getHome(userId);
        homeViewModel.getHomeLiveData().observe(getViewLifecycleOwner(), postList -> {
            // Update recycler adapter with post list data
            postRecyclerAdapter.setPostList(postList);
            postRecyclerAdapter.notifyDataSetChanged();
        });
        return root;
    }

    private void clearUserSession() {
        SharedPreferences preferences = requireContext().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    private void navigateToLoginPage() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}