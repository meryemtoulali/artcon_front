package com.example.artcon_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artcon_test.viewmodel.ProfileViewModel;

public class ProfileAboutFragment extends Fragment {
    private final String TAG="hatsunemiku";
    private ProfileViewModel profileViewModel;
    TextView bio;
    TextView location;
    TextView email;
    TextView phoneNumber;


    private static final String ARG_USER_ID = "userId";

    // TODO: Rename and change types of parameters
    private String userId;

    public ProfileAboutFragment() {
        // Required empty public constructor
    }
    public static ProfileAboutFragment newInstance(String userId) {
        ProfileAboutFragment fragment = new ProfileAboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Log.d(TAG, "viewModel created in fragment: " + profileViewModel);
        profileViewModel.getUserById(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_about, container, false);
        bio = rootView.findViewById(R.id.bio);
        location = rootView.findViewById(R.id.location);
        email = rootView.findViewById(R.id.email);
        phoneNumber = rootView.findViewById(R.id.phone);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe the user data
        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            Log.d(TAG, "Observer called in about fragment. User: " + user.toString());

            if (user != null) {
                // Update UI with user data
                Log.d(TAG, "user not null, user: " + user.toString());
                bio.setText(user.getBio());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
                bio.setText(user.getBio());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
            }
        });
    }
}