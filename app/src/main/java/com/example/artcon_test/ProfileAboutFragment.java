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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAboutFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    TextView bio;
    TextView location;
    TextView email;
    TextView phoneNumber;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileAboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAboutFragment newInstance(String param1, String param2) {
        ProfileAboutFragment fragment = new ProfileAboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Log.d("ProfileActivity//", "viewModel created in fragment: " + profileViewModel);
        profileViewModel.getUserById("1");
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
            Log.d("ProfileActivity//", "Observer called in about fragment. User: " + user.toString());

            if (user != null) {
                // Update UI with user data
                Log.d("ProfileActivity//", "user not null, user: " + user.toString());
                bio.setText(user.getBio());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
//                if (!TextUtils.isEmpty(user.getTitle())) {
//                    titleTextView.setVisibility(View.VISIBLE);
//                    titleTextView.setText(user.getTitle());
//                } else {
//                    // If the user's title is empty, hide the TextView
//                    titleTextView.setVisibility(View.GONE);
//                }
                bio.setText(user.getBio());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
            }
        });
    }
}