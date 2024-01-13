package com.example.artcon_test.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.artcon_test.repository.UserRepository;
import com.example.artcon_test.ui.login.LoginActivity;
import com.example.artcon_test.ui.portfolioPost.PortfolioPostFragment;
import com.example.artcon_test.ui.profile.ProfileFragmentAdapter;
import com.example.artcon_test.R;
import com.example.artcon_test.viewmodel.LogoutViewModel;
import com.example.artcon_test.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.widget.Toast;


public class ProfileFragment extends Fragment {

//    private FragmentProfileBinding binding;
    private ViewPager2 viewPager2;
    private boolean isFollowing;

    private boolean isArtist;
    private String USER_ID;
    private String selectedUserId = "37";

    private ProfileViewModel profileViewModel;

    private TabLayout tabLayout;
    private ProfileFragmentAdapter adapter;
    private LogoutViewModel logoutViewModel;
    private Button followButton;


    String TAG = "hatsunemiku";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        Bundle args = getArguments();
        if (args != null) {
            selectedUserId = args.getString("userId");
        }

        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        ImageView pfpImageView = view.findViewById(R.id.pfpImage);
        ImageView bannerImageView = view.findViewById(R.id.coverImage);
        TextView fullname = view.findViewById(R.id.fullname);
        TextView username = view.findViewById(R.id.username);
        TextView title = view.findViewById(R.id.title);
        TextView followers = view.findViewById(R.id.followers);
        TextView following = view.findViewById(R.id.following);
        ImageView kebabMenu = view.findViewById(R.id.kebab_menu);
        followButton = view.findViewById(R.id.followButton);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);

        USER_ID = preferences.getString("userId", null);
        if(selectedUserId != null){
            followButton.setVisibility(View.VISIBLE);
            profileViewModel.getUserById(selectedUserId);
            setupFollowButton();

        } else {
            followButton.setVisibility(View.GONE);
            profileViewModel.getUserById(USER_ID);
        }

        Picasso.get().setLoggingEnabled(true);

        // Observe the user data
        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            isArtist = "artist".equals(user.getType());

            if (user != null) {
                // Update UI with user data
                Picasso.get()
                        .load(user.getPicture())
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(pfpImageView);
                Picasso.get()
                        .load(user.getBanner())
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(bannerImageView);
                fullname.setText(user.getFirstname() + " " + user.getLastname());
                username.setText("@" + user.getUsername());
                if (!TextUtils.isEmpty(user.getTitle())) {
                    title.setVisibility(View.VISIBLE);
                    title.setText(user.getTitle());
                } else {
                    // If the user's title is empty, hide the TextView
                    title.setVisibility(View.GONE);
                }
                following.setText(String.valueOf(user.getFollowingCount()) + " Following");
                followers.setText(String.valueOf(user.getFollowersCount()) + " Followers");

            }
            updateTabLayout(isArtist);
        });

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

        return view;
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

    private void updateTabLayout(boolean isArtist) {

        tabLayout = getView().findViewById(R.id.tabLayout);
        viewPager2 = getView().findViewById(R.id.viewPager);

        tabLayout.removeAllTabs(); // Clear existing tabs

        if (isArtist) {
            tabLayout.addTab(tabLayout.newTab().setText("Portfolio"));
        }

        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        FragmentManager fragmentManager = getChildFragmentManager();
        if(selectedUserId != null) {
            adapter = new ProfileFragmentAdapter(fragmentManager, getLifecycle(), selectedUserId, isArtist);

        } else {
            adapter = new ProfileFragmentAdapter(fragmentManager, getLifecycle(), USER_ID, isArtist);

        }
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void setupFollowButton() {
        observeFollowStatus();

        followButton.setOnClickListener(v -> {
            if (isFollowing) {
                unfollowUser(USER_ID, selectedUserId);
            } else {
                followUser(USER_ID, selectedUserId);
            }
        });
    }

    private void observeFollowStatus() {
            profileViewModel.isFollowing(USER_ID, selectedUserId, new UserRepository.FollowCheckCallback() {
                @Override
                public void onSuccess(Boolean follows) {
                    if (follows) {
                        isFollowing = true;
                        followButton.setText("Following");
                    } else {
                        isFollowing = false;
                        followButton.setText("Follow");
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    // Handle error if needed
                    Log.e(TAG, errorMessage);
                }
            });
    }

    private void followUser(String currentUserId, String selectedUserId) {
        profileViewModel.followUser(currentUserId, selectedUserId, new UserRepository.FollowCallback() {
            @Override
            public void onSuccess() {
                isFollowing = true;
                followButton.setText("Unfollow");
                Log.d(TAG, "follow successful");
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    private void unfollowUser(String currentUserId, String selectedUserId) {
        profileViewModel.unfollowUser(currentUserId, selectedUserId, new UserRepository.FollowCallback() {
            @Override
            public void onSuccess() {
                isFollowing = false;
                followButton.setText("Follow");
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }
}