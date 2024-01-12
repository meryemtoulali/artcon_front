package com.example.artcon_test.chat.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.ItemContainerConversationBinding;
import com.example.artcon_test.chat.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerConversationBinding itemContainerConversationBinding = ItemContainerConversationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerConversationBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemContainerConversationBinding binding;
        ImageView imageProfile;

        UserViewHolder(ItemContainerConversationBinding itemContainerConversationBinding) {
            super(itemContainerConversationBinding.getRoot());
            binding = itemContainerConversationBinding;
            imageProfile = binding.imageProfile;  // Initialize the imageProfile from binding
        }

        void setUserData(User user) {
            Picasso.get().load(user.getPicture())
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .into(imageProfile);
            String fullName = user.getFirstname() + " " + user.getLastname();
            binding.textName.setText(fullName);
            binding.textUsername.setText(user.getUsername());
        }
    }
}
