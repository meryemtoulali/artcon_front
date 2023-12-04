package com.example.artcon_test;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProfileFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView pfpImageView = findViewById(R.id.pfpImage);
        Picasso.get().setLoggingEnabled(true);

        Picasso.get()
                .load("https://www.googleapis.com/download/storage/v1/b/artcon_media/o/images%2FWaS0rB.jpg?generation=1701640478556637&alt=media")
                .into(pfpImageView);
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


    }

}