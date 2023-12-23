package com.example.artcon_test.model;

import java.util.Date;

public class Post {
    private Integer id;
    private Date date;
    private String description;
    private String postImgURL;
    private String likes;
    private User user;

    public Post(Integer id, Date date, String description, String postImgURL, String likes, User user) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.postImgURL = postImgURL;
        this.likes = likes;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public void setPostImgURL(String postImgURL) {
        this.postImgURL = postImgURL;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}