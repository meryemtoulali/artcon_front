package com.example.artcon_test.ui.job;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.artcon_test.databinding.FragmentJobBinding;

public class JobFragment extends Fragment {

    private FragmentJobBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobViewModel dashboardViewModel =
                new ViewModelProvider(this).get(JobViewModel.class);

        binding = FragmentJobBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textJob;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}