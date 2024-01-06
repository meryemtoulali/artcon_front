package com.example.artcon_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddPostRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // Add any other fields you need to include in the response

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}