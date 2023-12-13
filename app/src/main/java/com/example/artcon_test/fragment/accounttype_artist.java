package com.example.artcon_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.artcon_test.R;
import com.example.artcon_test.model.ArtistType;
import com.example.artcon_test.model.UpdateUserViewModel;
import com.example.artcon_test.network.ArtistTypeService;
import com.example.artcon_test.viewmodel.ArtistTypeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accounttype_artist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accounttype_artist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ArtistType> artistTypes = new ArrayList<>();
    private ArrayAdapter<String> artistTypeAdapter;
    private Spinner spinner;
    UpdateUserViewModel user;

    public accounttype_artist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accounttype_artist.
     */
    // TODO: Rename and change types and number of parameters
    public static accounttype_artist newInstance(String param1, String param2) {
        accounttype_artist fragment = new accounttype_artist();
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
        View view = inflater.inflate(R.layout.fragment_accounttype_artist, container, false);
        user = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);
        spinner = view.findViewById(R.id.artist_title);
        populateSpinnerDropdown();
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        user.setTitle(spinner.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
        return view;
    }

    private void populateSpinnerDropdown() {
        ArtistTypeService artistTypeService = ArtistTypeViewModel.getArtistTypeService();
        Call<List<ArtistType>> call = artistTypeService.getArtistType();
        call.enqueue(new Callback<List<ArtistType>>() {
            @Override
            public void onResponse(Call<List<ArtistType>> call, Response<List<ArtistType>> response) {
                if (response.isSuccessful()) {
                    Log.d("spinner","Success");
                    artistTypes = response.body();
                    setupArtistTypeDropdown();
                } else {
                    Log.d("spinner", "artist type response has failed!");
                }
            }
            private void setupArtistTypeDropdown() {
                List<String> ArtistTypeNames = new ArrayList<>();
                Log.d("ArtistType","success setup");
                Log.d("ArtistType", "artistTypes size: " + artistTypes.size());
                ArtistTypeNames.add("Title"); // Add a hint or default value

                for (ArtistType artistType : artistTypes) {
                    Log.d("ArtistType", "artistTypes id: " + artistType.getArtist_type_name());
                    Log.d("ArtistType", "artistTypes name: " + artistType.getArtist_type_name());
                    ArtistTypeNames.add(artistType.getArtist_type_name());
                }

                artistTypeAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        ArtistTypeNames
                );
                artistTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(artistTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<ArtistType>> call, Throwable t) {
                Log.e("Artist Type", "Huge Error: " + t.getMessage());
            }
        });
    }


}