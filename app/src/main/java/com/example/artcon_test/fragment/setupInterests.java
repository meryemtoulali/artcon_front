package com.example.artcon_test.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.model.UpdateUserViewModel;
import com.example.artcon_test.network.InterestService;
import com.example.artcon_test.viewmodel.InterestViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private UpdateUserViewModel user;
    private List<Interest> interests = new ArrayList<>();
    private List<Long> interestId = new ArrayList<>();

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
        user = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);

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

        ((GridView) view.findViewById(R.id.grid_interest)).setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Interest interest = interests.get(position);

                        TextView textView = view.findViewById(R.id.interest_text);
                        textView.setTextColor(getResources().getColor(R.color.blackBase));

                        boolean isSelected = interest.isSelected();

                        isSelected = !isSelected;

                        // Update the interest object with the new selection state
                        interest.setSelected(isSelected);

                        if (isSelected) {
                            // Change color when selected
                            Drawable interest_selected = ContextCompat.getDrawable(getContext(),R.drawable.interest_selected);
                            textView.setTextColor(getResources().getColor(R.color.whiteBase));
                            textView.setBackground(interest_selected);
                            interestId.add(interest.getId());
                            user.setInterestIds(interestId);
                        } else {
                            // Return to normal color when not selected
                            Drawable interest_notSelected = ContextCompat.getDrawable(getContext(),R.drawable.interest_not_selected);
                            textView.setTextColor(getResources().getColor(R.color.blackBase));
                            textView.setBackground(interest_notSelected);
                            interestId.remove(interest.getId());
                            user.setInterestIds(interestId);
                        }
                    }
                }
        );

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