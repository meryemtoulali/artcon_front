package com.example.artcon_test.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.artcon_test.databinding.FragmentSearchPeopleBinding;

public class SearchPeopleFragment extends Fragment {

    private FragmentSearchPeopleBinding binding;
    private PeopleAdapter peopleAdapter;
    String TAG = "AllTooWell";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentSearchPeopleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize the adapter
        peopleAdapter = new PeopleAdapter();
        binding.peopleList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.peopleList.setAdapter(peopleAdapter);

        // Get the SearchViewModel instance
        SearchViewModel searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        // Observe the list of people from the SearchViewModel
        searchViewModel.searchPeopleLiveData.observe(getViewLifecycleOwner(), users -> {
            Log.d(TAG, "setPeopleList in Fragment: " + users.toString());
            peopleAdapter.setPeopleList(users);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

