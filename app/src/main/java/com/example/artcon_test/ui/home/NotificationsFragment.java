package com.example.artcon_test.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private String TAG = "hatsunemiku";
    private String userId;
    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;
    NotificationsViewModel notificationsViewModel;
    private NotificationsAdapter adapter;

    //On create method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);
        userId=preferences.getString("userId",null);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.notificationsList;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        notificationsViewModel.getNotifications(userId);
        notificationsViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), notificationList -> {
            adapter.setNotificationList(notificationList);
            adapter.notifyDataSetChanged();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}