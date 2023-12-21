package com.example.artcon_test.chat;

public class Conversation {
    private String userId;
    private String userName;
    private String pseudo;
    private String profilePictureUrl;
    private String lastMessage;
    private long timestamp;
    private String status;

    // Default Constructor
    public Conversation() {
        // Default constructor
    }

    // Constructor with userId
    public Conversation(String userId) {
        this.userId = userId;
    }

    // Constructor with timestamp
    public Conversation(long timestamp) {
        this.timestamp = timestamp;
    }

    // Method to format timestamp (as an example)
    public String getFormattedTimestamp() {
        // Implement the logic to format the timestamp as required
        // For example: Convert timestamp to a human-readable date-time format
        return "Formatted Timestamp"; // Replace with the formatted timestamp string
    }

    // Method to compare conversations based on timestamp (as an example)
    public int compareTo(Conversation other) {
        return Long.compare(this.timestamp, other.timestamp);
    }

    // Getter and Setter methods

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        // Add validation if needed
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        // Add validation if needed
        this.userName = userName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        // Add validation if needed
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        // Add validation if needed
        this.pseudo = pseudo;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        // Add validation if needed
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        // Add validation if needed
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // Add validation if needed
        this.status = status;
    }
}
