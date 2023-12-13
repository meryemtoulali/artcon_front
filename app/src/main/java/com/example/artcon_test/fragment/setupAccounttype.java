package com.example.artcon_test.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.UpdateUserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setupAccounttype#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setupAccounttype extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UpdateUserViewModel user;

    public setupAccounttype() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setupAccounttype.
     */
    // TODO: Rename and change types and number of parameters
    public static setupAccounttype newInstance(String param1, String param2) {
        setupAccounttype fragment = new setupAccounttype();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_accounttype, container, false);
        user = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);
        TextView artist = view.findViewById(R.id.artist);
        TextView not_artist = view.findViewById(R.id.normalUser);

        artist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Update the user type
                        user.setType("artist");
                        Log.d("Set up account type","type " + user.getType());
                        //Change the color of the background;
                        Drawable selected = ContextCompat.getDrawable(getContext(),R.drawable.account_type_selected);
                        artist.setBackground(selected);
                        Drawable not_selected = ContextCompat.getDrawable(getContext(),R.drawable.account_type);
                        not_artist.setBackground(not_selected);
                        //Remove the fragment
//                        getChildFragmentManager().popBackStack();
                        //Load the new one
                        loadFragment(new accounttype_artist());
                    }
                }
        );
        not_artist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setType("not_artist");
                        Log.d("Set up account type","type" + user.getType());
                        Drawable selected = ContextCompat.getDrawable(getContext(),R.drawable.account_type_selected);
                        not_artist.setBackground(selected);
                        Drawable not_selected = ContextCompat.getDrawable(getContext(),R.drawable.account_type);
                        artist.setBackground(not_selected);
                        //Remove the fragment
//                        getChildFragmentManager().popBackStack();
                        //Load the new one
                        loadFragment(new accounttype_notartist());
                    }
                }
        );
        return view;
    }

    private void loadFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.account_type, fragment)
                .commit();
    }
}