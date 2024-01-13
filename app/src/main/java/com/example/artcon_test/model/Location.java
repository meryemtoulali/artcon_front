package com.example.artcon_test.model;

public class Location {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Location(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
