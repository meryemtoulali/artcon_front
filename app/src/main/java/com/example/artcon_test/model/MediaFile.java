package com.example.artcon_test.model;


public class MediaFile {
    private Integer id;
    private String mediafile_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediafile_url() {
        return mediafile_url;
    }

    public void setMediafile_url(String mediafile_url) {
        this.mediafile_url = mediafile_url;
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "mediafileId=" + id +
                ", mediafileUrl='" + mediafile_url + '\'' +
                '}';
    }
}
