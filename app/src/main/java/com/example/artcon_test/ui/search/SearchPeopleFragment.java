package com.example.artcon_test.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentSearchPeopleBinding;

public class SearchPeopleFragment extends Fragment {

    private FragmentSearchPeopleBinding binding;
    private PeopleAdapter peopleAdapter;
    SearchViewModel searchViewModel;
    private ProgressBar progressBar;
    String TAG = "AllTooWell";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(requireParentFragment()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView PeopleFragment");
        binding = FragmentSearchPeopleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        progressBar = view.findViewById(R.id.progressBar);

        peopleAdapter = new PeopleAdapter();
        binding.peopleList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.peopleList.setAdapter(peopleAdapter);

        loading(true);

        searchViewModel.searchPeopleLiveData.observe(getViewLifecycleOwner(), users -> {
            Log.d(TAG, "setPeopleList in Fragment: " + users.toString());
            peopleAdapter.setPeopleList(users);

            TextView textViewNotFound = view.findViewById(R.id.textViewNoResultFound);
            if (users.isEmpty()) {
                textViewNotFound.setVisibility(View.VISIBLE);
            } else {
                textViewNotFound.setVisibility(View.GONE);
            }
            loading(false);
        });
        return view;
    }

    private void loading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


