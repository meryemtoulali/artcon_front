package com.example.artcon_test.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set OnClickListener for the FloatingActionButton
        binding.gotoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the ViewModel that the FAB is clicked
                homeViewModel.onFabClick();
            }
        });

        // Observe the LiveData for navigation
        homeViewModel.getNavigateToChat().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                // Navigate to the next page when LiveData changes
                Navigation.findNavController(view).navigate(R.id.nav_host_fragment_activity_bottom_navbar);

                // Reset the LiveData value to false to avoid multiple navigations
                homeViewModel.getNavigateToChat().setValue(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
