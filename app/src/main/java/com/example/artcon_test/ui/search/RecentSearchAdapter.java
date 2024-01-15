package com.example.artcon_test.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {
    private List<String> recentSearchList;
    private String TAG = "recent";
    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String searchQuery);
    }
    public RecentSearchAdapter(List<String> recentSearchList, OnItemClickListener onItemClickListener) {
        this.recentSearchList = recentSearchList;
        this.onItemClickListener = onItemClickListener;
        Log.d(TAG, "RecentSearchAdapter: " + recentSearchList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recent_search_item, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String recentSearch = recentSearchList.get(position);
        holder.recentSearchItem.setText(recentSearch);
        holder.recentSearchItem.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(recentSearch);
            }
        });
        holder.recentSearchItem.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            final int TOUCH_AREA_PADDING = 20;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableRight = holder.recentSearchItem.getRight();
                int drawableWidth = holder.recentSearchItem.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                int touchAreaEnd = drawableRight + TOUCH_AREA_PADDING;

                if (event.getRawX() >= (drawableRight - drawableWidth - TOUCH_AREA_PADDING) && event.getRawX() <= touchAreaEnd) {
                    removeItem(position);
                    return true;
                }
                return false;
            }
            return false;
        });
    }

    private void removeItem(int position) {
        recentSearchList.remove(position);
        Log.d(TAG, "removeItem: there");
        notifyItemRemoved(position);
        saveRecentSearches(recentSearchList);
    }

    private void saveRecentSearches(List<String> searches) {
        SharedPreferences preferences = context.getSharedPreferences("SearchHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> uniqueSearchesSet = new HashSet<>(searches);
        editor.putStringSet("SearchHistory", uniqueSearchesSet);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + recentSearchList.size());
            return recentSearchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recentSearchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentSearchItem = itemView.findViewById(R.id.recentSearchItem);
        }
    }
}
