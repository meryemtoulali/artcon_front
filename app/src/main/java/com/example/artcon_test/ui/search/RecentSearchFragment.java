package com.example.artcon_test.ui.search;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.artcon_test.databinding.FragmentRecentSearchBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecentSearchFragment extends Fragment implements RecentSearchAdapter.OnItemClickListener {

    private FragmentRecentSearchBinding binding;
    private List<String> recentSearchList;
    private RecentSearchAdapter recentSearchAdapter;
    private SearchViewModel searchViewModel;
    private String TAG = "recent";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentSearchList = new ArrayList<>();
        recentSearchList.addAll(loadRecentSearches());
        Log.d(TAG, "onCreate: " + recentSearchList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: recentSearchFragment");
        binding = FragmentRecentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        recentSearchAdapter = new RecentSearchAdapter(recentSearchList, this);
        Log.d(TAG, "onCreateView: " + recentSearchList);
        binding.recentSearchList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recentSearchList.setAdapter(recentSearchAdapter);


        return view;
    }

    private List<String> loadRecentSearches() {
        SharedPreferences preferences = requireContext().getSharedPreferences("SearchHistory", Context.MODE_PRIVATE);
        Set<String> recentSearchSet = preferences.getStringSet("SearchHistory", new HashSet<>());
        return new ArrayList<>(recentSearchSet);
    }

    @Override
    public void onItemClick(String searchQuery) {
        Log.d(TAG, "onItemClick: " + searchQuery);
        searchViewModel.searchPeople(searchQuery).observe(getViewLifecycleOwner(),
                users -> Log.d(TAG, "People " + users));
        searchViewModel.searchPosts(searchQuery).observe(getViewLifecycleOwner(),
                posts -> Log.d(TAG, "Posts " + posts));
    }
}
