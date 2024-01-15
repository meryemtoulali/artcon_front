package com.example.artcon_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.artcon_test.R;
import com.example.artcon_test.model.UpdateUserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accounttype_notartist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accounttype_notartist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UpdateUserViewModel user;

    public accounttype_notartist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accounttype_notartist.
     */
    // TODO: Rename and change types and number of parameters
    public static accounttype_notartist newInstance(String param1, String param2) {
        accounttype_notartist fragment = new accounttype_notartist();
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
        View view = inflater.inflate(R.layout.fragment_accounttype_notartist, container, false);
        user = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = view.findViewById(R.id.radioButton2);

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1){

            if(selectedRadioButtonId == R.id.radioButton1){
                user.setTitle("Organizer");
            }
            else {
                user.setTitle("");
            }
        }
        return view;
    }
}