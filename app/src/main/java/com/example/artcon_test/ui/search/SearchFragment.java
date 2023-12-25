package com.example.artcon_test.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment{

    private FragmentSearchBinding binding;
    private Button lastSelectedButton;
    private SearchViewModel searchViewModel;
    private Fragment currentFragment = null; // Track the current fragment
    String TAG = "AllTooWell";


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView arrowBack = root.findViewById(R.id.arrow_back);
        Button buttonSearchPeople = root.findViewById(R.id.buttonSearchPeople);
        Button buttonSearchPosts = root.findViewById(R.id.buttonSearchPosts);
        Button buttonSearchJobs = root.findViewById(R.id.buttonSearchJobs);
        EditText editTextSearch = root.findViewById(R.id.editTextSearch);

        // works
        editTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                final int TOUCH_AREA_PADDING = 16;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drawableRight = editTextSearch.getRight();
                    int drawableWidth = editTextSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    int touchAreaEnd = drawableRight + TOUCH_AREA_PADDING;

                    if (event.getRawX() >= (drawableRight - drawableWidth - TOUCH_AREA_PADDING) && event.getRawX() <= touchAreaEnd) {
                        handleSearchIconTouch(editTextSearch);
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });

        // works
        arrowBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        // works
        buttonSearchPeople.setOnClickListener(v -> {
            resetButtonColors();
            changeButtonColor(buttonSearchPeople);
            handleSearchButtonClick("people");
        });

        // works
        buttonSearchPosts.setOnClickListener(v -> {
            resetButtonColors();
            changeButtonColor(buttonSearchPosts);
            handleSearchButtonClick("posts");
        });

        // works
        buttonSearchJobs.setOnClickListener(v -> {
            resetButtonColors();
            changeButtonColor(buttonSearchJobs);
            handleSearchButtonClick("jobs");
        });
        return root;
    }

    // works
    private void handleSearchButtonClick(String searchType) {
        String searchQuery = binding.editTextSearch.getText().toString().trim();
        searchViewModel.setCurrentSearchType(searchType);
        Log.d(TAG, "HandleSearchButtonClick: " + searchType);
        resetButtonColors();
        if (!searchQuery.isEmpty()) {
            switch (searchType) {
                case "people" -> {
                    Log.d(TAG, "People HandleSearchButtonClick " + searchQuery);
                    currentFragment = new SearchPeopleFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("searchQuery", searchQuery);
                    currentFragment.setArguments(bundle);
                    replaceFragment(currentFragment);
                    changeButtonColor(binding.buttonSearchPeople);
                    searchViewModel.searchPeople(searchQuery).observe(getViewLifecycleOwner(), users -> {
                        Log.d(TAG, "People " + users);
                    });
                }
                case "posts" -> {
                    Log.d(TAG, "Posts HandleSearchButtonClick " + searchQuery);
                    changeButtonColor(binding.buttonSearchPosts);
                    replaceFragment(new SearchPostsFragment());
                }
                case "jobs" -> {
                    Log.d(TAG, "Jobs HandleSearchButtonClick " + searchQuery);
                    changeButtonColor(binding.buttonSearchJobs);
                    replaceFragment(new SearchJobsFragment());
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
        }
    }

    //works
    private void handleSearchIconTouch(EditText editTextSearch) {
        String searchQuery = editTextSearch.getText().toString().trim();
        Log.d(TAG,"HandleSearchIconTouch " + searchQuery);
        if (!searchQuery.isEmpty()) {
            binding.buttonSearchPeople.performClick();
            //handleSearchButtonClick(searchViewModel.getCurrentSearchType());
            searchViewModel.searchPeople(searchQuery).observe(getViewLifecycleOwner(), users -> {
                Log.d(TAG, "search Fragment " + users);
                    });
        } else {
            Toast.makeText(requireContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"searchTypeError");
        }
    }

    // works
    private void resetButtonColors() {
        if (lastSelectedButton != null) {
            lastSelectedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.grey_border));
        }
    }
    // works
    private void changeButtonColor(Button selectedButton) {
        selectedButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_button));
        lastSelectedButton = selectedButton;
    }

    // works
    private void replaceFragment(Fragment fragment) {
        Log.d(TAG, "Replacing fragment with: " + fragment.getClass().getSimpleName());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerSearch, fragment);
        transaction.commitNow();
        Log.d(TAG, "Is fragment visible: " + fragment.isVisible()); //false
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}