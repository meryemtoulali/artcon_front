package com.example.artcon_test.model;

public class ArtistType {
    private Long Id;
    private String artist_type_name;

    public ArtistType(Long artist_type_id, String artist_type_name) {
        this.Id = artist_type_id;
        this.artist_type_name = artist_type_name;
    }

    public ArtistType() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getArtist_type_name() {
        return artist_type_name;
    }

    public void setArtist_type_name(String artist_type_name) {
        this.artist_type_name = artist_type_name;
    }
}
