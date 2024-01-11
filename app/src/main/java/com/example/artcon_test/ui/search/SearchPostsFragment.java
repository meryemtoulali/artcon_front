package com.example.artcon_test.ui.search;

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

import com.example.artcon_test.databinding.FragmentSearchPeopleBinding;

public class SearchPostsFragment extends Fragment {
//    private FragmentSearchPostsBinding binding;
//    private PostsAdapter postsAdapter;
//    SearchViewModel searchViewModel;
//    String TAG = "AllTooWell";
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        searchViewModel = new ViewModelProvider(requireParentFragment()).get(SearchViewModel.class);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView PostsFragment");
//        binding = FragmentSearchPostsBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//
//        postsAdapter = new PostsAdapter();
//        binding.postsList.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.postsList.setAdapter(postsAdapter);
//
//        searchViewModel.searchPostsLiveData.observe(getViewLifecycleOwner(), posts -> {
//            Log.d(TAG, "setPostsList in Fragment: " + posts.toString());
//            postsAdapter.setPostsList(posts);
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}
