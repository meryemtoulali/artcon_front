package com.example.artcon_test;

import static android.text.TextUtils.isEmpty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.artcon_test.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private ProfileViewModel profileViewModel;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProfileFragmentAdapter adapter;

    String TAG = "hatsunemiku";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // bind to views
        ImageView pfpImageView = findViewById(R.id.pfpImage);
        ImageView bannerImageView = findViewById(R.id.coverImage);
        TextView fullname = findViewById(R.id.fullname);
        TextView username = findViewById(R.id.username);
        TextView title = findViewById(R.id.title);
        TextView followers = findViewById(R.id.followers);
        TextView following = findViewById(R.id.following);
        Picasso.get().setLoggingEnabled(true);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Log.d(TAG, "viewModel created: " + profileViewModel);

        // Call getUserById to fetch user data
        profileViewModel.getUserById("4");
        // Observe the user data
        profileViewModel.getUserLiveData().observe(this, user -> {
            Log.d(TAG, "Observer called. User: " + user.toString());

            if (user != null) {
                // Update UI with user data
                Log.d(TAG, "user not null: " + user.toString());
                Picasso.get()
                        .load(user.getPicture())
                        .into(pfpImageView);
                Picasso.get()
                        .load(user.getBanner())
                        .into(bannerImageView);
                fullname.setText(user.getFirstname() + " " + user.getLastname());
                username.setText(user.getUsername());
                if (!isEmpty(user.getTitle())) {
                    title.setVisibility(View.VISIBLE);
                    title.setText(user.getTitle());
                } else {
                    // If the user's title is empty, hide the TextView
                    title.setVisibility(View.GONE);
                }
                following.setText(String.valueOf(user.getFollowingCount()) + " Following");
                followers.setText(String.valueOf(user.getFollowersCount()) + " Followers");

            }
        });
//        Picasso.get()
//                .load("https://www.googleapis.com/download/storage/v1/b/artcon_media/o/images%2FWaS0rB.jpg?generation=1701640478556637&alt=media")
//                .into(pfpImageView);

        // tab logic
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Portfolio"));
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ProfileFragmentAdapter(fragmentManager, getLifecycle());
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
        // end of tab logic


    }

}