package com.example.artcon_test.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.network.InterestService;
import com.example.artcon_test.viewmodel.InterestViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setupInterests#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setupInterests extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Interest> interests = new ArrayList<>();

    public setupInterests() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setupInterests.
     */
    // TODO: Rename and change types and number of parameters
    public static setupInterests newInstance(String param1, String param2) {
        setupInterests fragment = new setupInterests();
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
        View view = inflater.inflate(R.layout.fragment_setup_interests, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.grid_interest);

        InterestService interestService = InterestViewModel.getInterests();
        Call<List<Interest>> call = interestService.getInterests();
        call.enqueue(new Callback<List<Interest>>() {
            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                if(response.isSuccessful()){
                    interests = response.body();
                    Log.d("Interest", "Interests: " + interests);
                    // Interest Adapter
                    InterestAdapter interestAdapter = new InterestAdapter(getContext().getApplicationContext(), R.layout.interest_layout,interests);
                    gridView.setAdapter(interestAdapter);
                }
                else {
                    Log.d("Interest", "Interest response has failed!");
                }
            }
            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.e("Interest", "Huge Error: " + t.getMessage());
            }
        });



        return view;
    }

    public class InterestAdapter extends ArrayAdapter<Interest> {

        private List<Interest> interests;

        public InterestAdapter(@NonNull Context context, int resource, List<Interest> interests) {
            super(context, resource, interests);
            this.interests = interests;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.interest_layout, parent, false);

            //
            TextView textView = convertView.findViewById(R.id.interest_text);
            textView.setText(interests.get(position).getInterest_name());
            return convertView;
        }
    }
}