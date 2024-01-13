package com.example.artcon_test.model;

public class MediaItem {
    private Integer Id;
    private String mediafile_url;

    public Integer getId() {
        return Id;
    }

    public MediaItem(String mediaUri) {
        this.mediafile_url = mediaUri;
    }

    public String getMediafile_url() {
        return mediafile_url;
    }
}