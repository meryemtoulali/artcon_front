package com.example.artcon_test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.util.Log;

public class ProfileFragmentAdapter extends FragmentStateAdapter {
    private String TAG="hatsunemiku";
    private String userId;
    private boolean isArtist;
    public ProfileFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String userId, boolean isArtist) {
        super(fragmentManager, lifecycle);
        this.userId = userId;
        this.isArtist = isArtist;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            if(isArtist) return ProfilePortfolioFragment.newInstance(userId);
            else return  ProfilePostsFragment.newInstance(userId);
        } else if (position == 1) {
            if(isArtist) return  ProfilePostsFragment.newInstance(userId);
            else return  ProfileAboutFragment.newInstance(userId);
        } else if(position == 2) {
            return ProfileAboutFragment.newInstance(userId);
        } else {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
//        if(isArtist){
//            if (position == 0) {
//                return new ProfilePortfolioFragment();
//            } else if (position == 1) {
//                return new ProfilePostsFragment();
//            } else {
//                return new ProfileAboutFragment();
//            }
//        } else {
//            if (position == 0){
//                return new ProfilePortfolioFragment();
//            } else {
//                return new ProfileAboutFragment();
//            }
//        }

//        if (position == 0) {
//            return new ProfilePortfolioFragment();
//        } else if (position == 1) {
//            return new ProfilePostsFragment();
//        } else{
//            return new ProfileAboutFragment();
//        }
    }

    @Override
    public int getItemCount() {
//        return isArtist ? 3 : 2;
        return isArtist ? 3 : 2;
    }

}
