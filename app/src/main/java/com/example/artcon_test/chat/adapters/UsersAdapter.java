package com.example.artcon_test.chat.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.chat.listeners.UserListener;
import com.example.artcon_test.databinding.ItemContainerConversationBinding;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.chat.models.ChatMessage;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users;
    private static UserListener userListener;

    public UsersAdapter(List<User> users,UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
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
        Picasso.get().setLoggingEnabled(true);
        User user = users.get(position);

        String pictureUri = user.getPicture();
        Log.d("UsersAdapter", "Picture URI: " + pictureUri);

        Picasso.get()
                .load(pictureUri)
                .placeholder(R.drawable.profile_picture_placeholder)
                .into(holder.imageProfile);
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
            imageProfile = binding.imageProfile;
        }

        void setUserData(User user) {
            Log.d("UsersAdapter", "Setting user data: " + user.toString());
            ImageView imageViewProfile;
            imageViewProfile = itemView.findViewById(R.id.imageProfile);
            String fullName = user.getFirstname() + " " + user.getLastname();
            binding.textName.setText(fullName);
            binding.textUsername.setText(user.getUsername());
            String pictureUri = user.getPicture();
//            binding.imageProfile.setImageURI(Uri.parse(pictureUri));
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }

    }
}
