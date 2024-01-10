package com.example.artcon_test.model;

import java.util.Date;
import java.util.List;

public class Post {
    private Integer id;
    private String description;
    private User user;
    private List<MediaFile> mediaFiles;
    private Integer likes;
    private Date dateTime;

    public Post(Integer id, String description, User user, List<MediaFile> mediaFiles, Integer likes, Date dateTime) {

        this.id = id;
        this.description = description;
        this.user = user;
        this.mediaFiles = mediaFiles;
        this.likes = likes;
        this.dateTime = dateTime;
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

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
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

}


