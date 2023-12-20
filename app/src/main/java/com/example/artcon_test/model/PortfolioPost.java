package com.example.artcon_test.model;

import java.util.Date;

public class PortfolioPost {
    private Integer id;
    private Date date;
    private String title;
    private String caption;
    private String media;
    private String username;

    public PortfolioPost() {
    }

    public PortfolioPost(Integer id, Date date, String title, String caption, String media) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.caption = caption;
        this.media = media;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "PortfolioPost{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", media='" + media + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}



