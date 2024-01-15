package com.example.artcon_test.ui.search;

import static android.text.TextUtils.isEmpty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<User> peopleList = new ArrayList<>();
    String TAG = "AllTooWell";

    public void setPeopleList(List<User> peopleList) {
        this.peopleList = peopleList;
        Log.d(TAG, "set peopleList" + peopleList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_people_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = peopleList.get(position);
        holder.textViewFullName.setText(user.getFirstname() + " " + user.getLastname());
        holder.textViewUsername.setText("@" + user.getUsername());
        if (!isEmpty(user.getTitle())) {
            holder.textViewTitle.setVisibility(View.VISIBLE);
            holder.textViewTitle.setText(user.getTitle());
        } else {
            holder.textViewTitle.setVisibility(View.GONE);
        }
        Picasso.get().load(user.getPicture())
                .placeholder(R.drawable.picasso_placeholder) // Placeholder image
                .into(holder.imageViewProfile);

        // view profile onclick
        holder.itemView.setOnClickListener(v -> {
            String selectedUserId = peopleList.get(position).getId().toString();
            onUserItemClick(selectedUserId);
        });

    }

    public interface OnUserItemClickListener {
        void onUserItemClick(String userId);
    }

    private OnUserItemClickListener userItemClickListener;
    // Setter for the listener
    public void setOnUserItemClickListener(OnUserItemClickListener listener) {
        this.userItemClickListener = listener;
    }

    // Method to handle item click and pass the user ID
    private void onUserItemClick(String userId) {
        if (userItemClickListener != null) {
            userItemClickListener.onUserItemClick(userId);
        }
    }



    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName;
        TextView textViewUsername;
        TextView textViewTitle;
        ImageView imageViewProfile;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewFullName = itemView.findViewById(R.id.fullnamePeople);
            textViewUsername = itemView.findViewById(R.id.usernamePeople);
            textViewTitle = itemView.findViewById(R.id.titlePeople);
            imageViewProfile = itemView.findViewById(R.id.pfpImagePeople);
        }
    }
}
