package com.example.artcon_test.model;

public class Interest {
    private Long id;
    private String interest_name;
    private boolean selected;

    public Interest(Long interest_id, String interest_name) {
        this.id = interest_id;
        this.interest_name = interest_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
