package com.example.artcon_test.chat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.artcon_test.R;
import com.example.artcon_test.chat.model.Message;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textName.setText(message.getSenderName());
        holder.textUsername.setText(message.getSenderUsername());
        holder.lastMessage.setText(message.getContent());
        holder.timestamp.setText(message.getTimestamp());

        // Load profile image using Glide
        Glide.with(holder.itemView.getContext())
                .load(message.getProfileImageUrl())
                .placeholder(R.drawable.profile)
                .into(holder.imageProfile);

    }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textUsername;
        TextView lastMessage;
        TextView timestamp;
        RoundedImageView imageProfile;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textUsername = itemView.findViewById(R.id.textUsername);
            lastMessage = itemView.findViewById(R.id.LastMessage);
            timestamp = itemView.findViewById(R.id.timestamp);
            imageProfile = itemView.findViewById(R.id.imageProfile); // Initialize the RoundedImageView
        }
    }
}

