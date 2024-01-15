package com.example.artcon_test.model;

import java.util.Date;
import java.util.List;
import okhttp3.MultipartBody;

public class Post {
    private Integer id;
    private String description;
    private User user;
    private List<MediaItem> mediaFiles;
    private Integer likes;
    private Integer comments_count;

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    private Date dateTime;
    private Interest interest;
    private Date date;

    public Post(Integer id, String description, User user, List<MediaItem> mediaFiles, Integer likes, Integer comments_count, Date dateTime) {

        this.id = id;
        this.description = description;
        this.user = user;
        this.mediaFiles = mediaFiles;
        this.likes = likes;
        this.dateTime = dateTime;
        this.comments_count = comments_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public List<MediaItem> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaItem> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Post{")
                .append("id=").append(id)
                .append(", description='").append(description).append('\'');

        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            stringBuilder.append(", mediaFiles.get(0) =").append(mediaFiles.get(0).toString());
        } else {
            stringBuilder.append(", mediaFiles=[]");
        }

        stringBuilder.append(", likes=").append(likes)
                .append(", dateTime=").append(dateTime)
                .append('}');

        return stringBuilder.toString();
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
