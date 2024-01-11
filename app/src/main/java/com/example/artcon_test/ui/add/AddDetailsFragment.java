// AddDetailsFragment.java
package com.example.artcon_test.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.artcon_test.R;

public class AddDetailsFragment extends Fragment {

    public AddDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_details, container, false);

        // Find and set up your buttons here (New post and New portfolio post)

        return view;
    }
}
