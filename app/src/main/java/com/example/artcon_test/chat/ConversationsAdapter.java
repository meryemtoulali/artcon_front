package com.example.artcon_test.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.artcon_test.R;
import java.util.List;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {

    private List<Conversation> conversations;
    private OnItemClickListener onItemClickListener;

    public ConversationsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();  // Refresh the list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (conversations == null || conversations.isEmpty()) return;

        Conversation conversation = conversations.get(position);
        holder.userNameTextView.setText(conversation.getUserName());
        holder.pseudoTextView.setText(conversation.getPseudo());
        holder.lastMessageTextView.setText(conversation.getLastMessage());
        holder.timestampTextView.setText(String.valueOf(conversation.getTimestamp()));
        holder.statusTextView.setText(conversation.getStatus());
        // Load profile picture using Glide or any other library
        // Glide.with(holder.itemView.getContext()).load(conversation.getProfilePictureUrl()).into(holder.profilePictureImageView);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(conversation));
    }

    @Override
    public int getItemCount() {
        return conversations != null ? conversations.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePictureImageView;
        TextView userNameTextView;
        TextView pseudoTextView;
        TextView lastMessageTextView;
        TextView timestampTextView;
        TextView statusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            pseudoTextView = itemView.findViewById(R.id.pseudoTextView);
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Conversation conversation);
    }
}
