package com.example.artcon_test.chat.model;

public class Message {
    private String messageId;
    private String profileImageUrl;
    private String senderName;
    private String senderUsername;
    private String content;
    private String timestamp;

    // Constructor to match the data
    public Message(String messageId, String profileImageUrl, String senderName, String senderUsername, String content, String timestamp) {
        this.messageId = messageId;
        this.profileImageUrl = profileImageUrl;
        this.senderName = senderName;
        this.senderUsername = senderUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProfileImageUrl() {
        return "https://via.placeholder.com/150";
//        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
