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
import com.example.artcon_test.databinding.FragmentSearchPostsBinding;
import com.example.artcon_test.ui.profile.PostAdapter;
import com.example.artcon_test.viewmodel.SearchViewModel;

public class SearchPostsFragment extends Fragment {
    private FragmentSearchPostsBinding binding;
    private PostAdapter postsAdapter;
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
        Log.d(TAG, "onCreateView PostsFragment");
        binding = FragmentSearchPostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        progressBar = view.findViewById(R.id.progressBar);

        postsAdapter = new PostAdapter(requireContext());
        binding.postsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.postsList.setAdapter(postsAdapter);

        loading(true);

        searchViewModel.getSearchPostsLiveData().observe(getViewLifecycleOwner(), posts -> {
            Log.d(TAG, "setPostsList in Fragment: " + posts.toString());
            postsAdapter.setPostList(posts);

            TextView textViewNotFound = view.findViewById(R.id.textViewNoResultFound);
                    if (posts.isEmpty()) {
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
