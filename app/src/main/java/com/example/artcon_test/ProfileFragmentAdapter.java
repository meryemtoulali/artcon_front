package com.example.artcon_test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileFragmentAdapter extends FragmentStateAdapter {

    public ProfileFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ProfilePortfolioFragment();
        } else if (position == 1) {
            return new ProfilePostsFragment();
        } else {
            return new ProfileAboutFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
