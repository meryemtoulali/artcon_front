package com.example.artcon_test.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.artcon_test.R;
import com.example.artcon_test.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import android.content.Context;


public class ProfileFragment extends Fragment {

//    private FragmentProfileBinding binding;
    private ViewPager2 viewPager2;

    private boolean isArtist;
    private String USER_ID;
    private ProfileViewModel profileViewModel;

    private TabLayout tabLayout;
    private ProfileFragmentAdapter adapter;

    String TAG = "hatsunemiku";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ImageView pfpImageView = view.findViewById(R.id.pfpImage);
        ImageView bannerImageView = view.findViewById(R.id.coverImage);
        TextView fullname = view.findViewById(R.id.fullname);
        TextView username = view.findViewById(R.id.username);
        TextView title = view.findViewById(R.id.title);
        TextView followers = view.findViewById(R.id.followers);
        TextView following = view.findViewById(R.id.following);
        Picasso.get().setLoggingEnabled(true);

        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);

        USER_ID = preferences.getString("userId", null);


        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Log.d(TAG, "viewModel created: " + profileViewModel);
        // Call getUserById to fetch user data
        profileViewModel.getUserById(USER_ID);


        // Observe the user data
        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            Log.d(TAG, "Observer called. User: " + user.toString());
            isArtist = "artist".equals(user.getType());
            Log.d(TAG, "isArtist:" + isArtist);

            if (user != null) {
                // Update UI with user data
                Log.d(TAG, "user not null: " + user.toString());

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
        return view;
    }

    private void updateTabLayout(boolean isArtist) {
        Log.d(TAG, "isArtist in tab logic:" + isArtist);

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
        adapter = new ProfileFragmentAdapter(fragmentManager, getLifecycle(), USER_ID, isArtist);
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


}