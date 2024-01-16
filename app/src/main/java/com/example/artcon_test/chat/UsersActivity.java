package com.example.artcon_test.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artcon_test.chat.adapters.UsersAdapter;
import com.example.artcon_test.chat.listeners.UserListener;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.example.artcon_test.databinding.ActivityUsersBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BasicActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        // Log the user ID before calling getUsers()
        Log.d("UsersActivity", "Before calling getUsers(): " + getCurrentUserId());

        // Set the layout manager for the RecyclerView
        binding.usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch and display users
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.actionBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), MainNavActivity.class)));

    }

    private String getCurrentUserId() {
        // Retrieve the current user ID from wherever it is stored
        return preferenceManager.getString(Constants.KEY_USER_ID);
    }


    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener( task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.setFirstname(queryDocumentSnapshot.getString(Constants.KEY_FIRSTNAME));
                            user.setLastname(queryDocumentSnapshot.getString(Constants.KEY_LASTNAME));
                            user.setUsername(queryDocumentSnapshot.getString(Constants.KEY_USERNAME));
                            user.setPicture(queryDocumentSnapshot.getString(Constants.KEY_IMAGE));
                            user.setToken(queryDocumentSnapshot.getString(Constants.KEY_TOKEN));
                            user.setUserId(queryDocumentSnapshot.getString(Constants.KEY_USER_ID));
                            user.setId(queryDocumentSnapshot.getId());
                            users.add(user);
                        }
                        if (users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }
    private void showErrorMessage() {
        binding.texterrorMessage.setText("No user available");
        binding.texterrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER,user);
        startActivity(intent);
        finish();
    }
}