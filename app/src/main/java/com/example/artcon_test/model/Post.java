package com.example.artcon_test.model;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;

public class Post {
    private Integer id;
    private String description;
    private User user;
    private Interest interest;
    private List<MediaItem> mediaFiles;
    private Integer likes;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
