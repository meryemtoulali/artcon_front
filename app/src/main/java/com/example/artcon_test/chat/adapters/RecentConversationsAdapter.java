package com.example.artcon_test.chat.adapters;

import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.chat.listeners.ConversionListener;
import com.example.artcon_test.chat.models.ChatMessage;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.databinding.ItemContainerConversationBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerConversationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
        Picasso.get().setLoggingEnabled(true);
        ChatMessage chatMessage = chatMessages.get(position);

        String pictureUri = chatMessage.conversionImage;
        Log.d("UsersAdapter", "Picture URI: " + pictureUri);

        Picasso.get()
                .load(pictureUri)
                .placeholder(R.drawable.profile_picture_placeholder)
                .into(holder.imageProfile);

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{
        ItemContainerConversationBinding binding;
        ImageView imageProfile;

        ConversionViewHolder(ItemContainerConversationBinding itemContainerConversationBinding){
            super(itemContainerConversationBinding.getRoot());
            binding = itemContainerConversationBinding;
            imageProfile = binding.imageProfile;
        }

        void setData(ChatMessage chatMessage) {
            ImageView imageViewProfile;
            imageViewProfile = itemView.findViewById(R.id.imageProfile);
            String fullName = chatMessage.conversionFirstname + chatMessage.conversionLastname;
            binding.textName.setText(fullName);
            binding.textUsername.setText(chatMessage.conversionUsername);
            binding.textRecentMessage.setText((chatMessage.message));
            binding.getRoot().setOnClickListener( v -> {
                User user = new User();
                user.setUserId(chatMessage.conversionId) ;
                user.setPicture(chatMessage.conversionImage);
                user.setFirstname(chatMessage.conversionFirstname);
                user.setLastname(chatMessage.conversionLastname);
                user.setUsername(chatMessage.conversionUsername);
                conversionListener.onConversionCLicked(user);

            });
        }
    }
}
