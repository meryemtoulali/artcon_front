package com.example.artcon_test.ui.post;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Comment;
import com.example.artcon_test.model.MediaItem;
import com.example.artcon_test.model.User;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;
    private VideoView currentVideoView;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        User user = comment.getUser();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        holder.userName.setText(firstName + " " + lastName);

        String imageUrl = user.getPicture();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.photoProfile);

        String Content = comment.getContent();
        holder.content.setText(Content);

        Date time = comment.getDate();

       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //String formattedDate = sdf.format(time);
        Log.d("time", "before date"+ time);
        String timeString = String.valueOf(time);
        Log.d("time", "before string"+ timeString);

        String timeFin = formatDateTime(timeString);
Log.d("time", "after"+ timeFin);
        holder.time.setText(timeFin);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoProfile;
        TextView userName, content, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoProfile = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.profileName);
            content = itemView.findViewById(R.id.commentContent);
            time = itemView.findViewById(R.id.commentDate);

        }

    }

    public static String formatDateTime(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat;

        try {
            Date date = inputFormat.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Calendar currentCalendar = Calendar.getInstance();

            if (isSameDay(calendar, currentCalendar)) {
                // If it's the same day, show only time
                outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            } else if (isYesterday(calendar, currentCalendar)) {
                // If it's yesterday, show "Yesterday"
                return "Yesterday";
            } else {
                // Otherwise, show the full date
                outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            }

            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Return original string if parsing fails
        }
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isYesterday(Calendar cal1, Calendar cal2) {
        cal2.add(Calendar.DAY_OF_MONTH, -1);
        return isSameDay(cal1, cal2);
    }

}
