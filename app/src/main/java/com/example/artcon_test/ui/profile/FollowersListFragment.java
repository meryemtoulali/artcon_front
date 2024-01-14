package com.example.artcon_test.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.FragmentFollowersListBinding;
import com.example.artcon_test.ui.search.PeopleAdapter;
import com.example.artcon_test.viewmodel.ProfileViewModel;

public class FollowersListFragment extends Fragment implements PeopleAdapter.OnUserItemClickListener {
    private String TAG = "hatsunemiku";
    private String userId;
    private FragmentFollowersListBinding binding;
    private RecyclerView recyclerView;
    private ProfileViewModel profileViewModel;
    private PeopleAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Bundle args = getArguments();
        if (args != null) {
            userId = args.getString("selectedUserId");
        }
        if(userId == null){
            SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);
            userId=preferences.getString("userId",null);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        recyclerView = binding.followersList;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter();
        adapter.setOnUserItemClickListener(this);
        binding.followersList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.followersList.setAdapter(adapter);

        profileViewModel.getFollowersList(userId);
        profileViewModel.getFollowersListLiveData().observe(getViewLifecycleOwner(), followers -> {
            adapter.setPeopleList(followers);
//            TextView textViewNotFound = view.findViewById(R.id.textViewNoResultFound);
//            if (followers.isEmpty()) {
//                textViewNotFound.setVisibility(View.VISIBLE);
//            } else {
//                textViewNotFound.setVisibility(View.GONE);
//            }
//            loading(false);
        });
        return view;
    }

    @Override
    public void onUserItemClick(String userId) {
        Bundle args = new Bundle();
        args.putString("selectedUserId", userId);
        Navigation.findNavController(requireView()).navigate(R.id.action_followers_to_profile, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
