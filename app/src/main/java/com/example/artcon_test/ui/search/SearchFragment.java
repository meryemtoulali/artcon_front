package com.example.artcon_test.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.artcon_test.ui.login.LoginActivity;
import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentSearchBinding;
import com.example.artcon_test.viewmodel.LogoutViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class SearchFragment extends Fragment{

    private static final int MAX_RECENT_SEARCHES = 8;
    private FragmentSearchBinding binding;
    private Button lastSelectedButton;
    private LogoutViewModel logoutViewModel;
    private SearchViewModel searchViewModel;
    private RecentSearchFragment recentSearchFragment;
    String TAG = "AllTooWell";


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView arrowBack = root.findViewById(R.id.arrow_back);
        Button buttonSearchPeople = root.findViewById(R.id.buttonSearchPeople);
        Button buttonSearchPosts = root.findViewById(R.id.buttonSearchPosts);
        Button buttonSearchJobs = root.findViewById(R.id.buttonSearchJobs);
        EditText editTextSearch = root.findViewById(R.id.editTextSearch);
        ImageView kebabMenu = root.findViewById(R.id.kebab_menu);

        replaceFragment(new RecentSearchFragment());

        // works
        editTextSearch.setOnTouchListener((v, event) -> {
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
        });

        // works
        arrowBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

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
                    replaceFragment(new SearchPeopleFragment());
                    changeButtonColor(binding.buttonSearchPeople);
                    searchViewModel.searchPeople(searchQuery).observe(getViewLifecycleOwner(),
                            users -> Log.d(TAG, "People " + users));
                    searchViewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
                        if (message != null) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                case "posts" -> {
                    Log.d(TAG, "Posts HandleSearchButtonClick " + searchQuery);
                    changeButtonColor(binding.buttonSearchPosts);
                    replaceFragment(new SearchPostsFragment());
                    // here
                    searchViewModel.searchPosts(searchQuery).observe(getViewLifecycleOwner(), posts -> {
                        Log.d(TAG, "Posts " + posts);
                        Log.d(TAG,"posts res" + posts);
                    });
                    searchViewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
                        if (message != null) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
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
            saveRecentSearch(searchQuery);
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
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerSearch, fragment)
                .commit();
    }

    @SuppressLint("MutatingSharedPrefs")
    private void saveRecentSearch(String searchQuery) {
        SharedPreferences preferences = requireContext().getSharedPreferences("SearchHistory", Context.MODE_PRIVATE);
        Set<String> recentSearches = preferences.getStringSet("SearchHistory", new HashSet<>());

        Log.d(TAG, "saveRecentSearch: " +recentSearches);
        recentSearches.add(searchQuery);
        Log.d(TAG, "saveRecentSearch: " +recentSearches);

        if (recentSearches.size() > MAX_RECENT_SEARCHES) {
            Iterator<String> iterator = recentSearches.iterator();
            iterator.next();
            iterator.remove();
        }

        preferences.edit().putStringSet("SearchHistory", recentSearches).apply();
        Log.d(TAG, "saveRecentSearch: " + recentSearches);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}