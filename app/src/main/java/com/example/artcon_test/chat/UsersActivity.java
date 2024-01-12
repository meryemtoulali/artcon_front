package com.example.artcon_test.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artcon_test.chat.adapters.UsersAdapter;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.example.artcon_test.databinding.ActivityUsersBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        getUsers();
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        if (currentUserId == null) {
                            // Handle the null value (log, show an error message, etc.)
                            Log.e("UsersActivity", "Current User ID is null");
                            return;
                        }

                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                Log.d("UserActivity", "User ID: " + currentUserId);
                            }

                            if (currentUserId.equals(preferenceManager.getString(Constants.KEY_USER_ID))) {
                                continue;
                            }

                            User user = new User();
                            user.setFirstname(queryDocumentSnapshot.getString(Constants.KEY_FIRSTNAME));
                            user.setLastname(queryDocumentSnapshot.getString(Constants.KEY_LASTNAME));
                            user.setUsername(queryDocumentSnapshot.getString(Constants.KEY_USERNAME));
                            user.setFirstname(queryDocumentSnapshot.getString(Constants.KEY_FIRSTNAME));
                            user.setToken(queryDocumentSnapshot.getString(Constants.KEY_TOKEN));
                            users.add(user);
                        }                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage() {
        binding.texterrorMessage.setText(String.format("%s", "No user available"));
        binding.texterrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
