package com.example.artcon_test.model;

public class Interest {
    private Long interest_id;
    private String interest_name;

    public Interest(Long interest_id, String interest_name) {
        this.interest_id = interest_id;
        this.interest_name = interest_name;
    }

    public Long getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Long interest_id) {
        this.interest_id = interest_id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }
}
