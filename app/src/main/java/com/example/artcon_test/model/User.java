package com.example.artcon_test.model;

import java.util.Date;

public class User {
    private Integer id;
    private String role;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String passwordHash;
    private String type;
    private String title;
    private Date birthday;
    private String picture;
    private String banner;
    private String phone_number;
    private String bio;
    private String location;
    private Integer followers_count;
    private Integer following_count;

    public User() {
    }


    public User(Integer id, String role, String firstname, String lastname, String username, String email, String passwordHash, String type, String title, Date birthday, String picture, String banner, String phoneNumber, String bio, String location, Integer followersCount, Integer followingCount) {

        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.type = type;
        this.title = title;
        this.birthday = birthday;
        this.picture = picture;
        this.banner = banner;
        this.phone_number = phoneNumber;
        this.bio = bio;
        this.location = location;
        this.followers_count = followersCount;
        this.following_count = followingCount;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getFollowersCount() {
        return followers_count;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followers_count = followersCount;
    }

    public Integer getFollowingCount() {
        return following_count;
    }

    public void setFollowingCount(Integer followingCount) {
        this.following_count = followingCount;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", birthday=" + birthday +
                ", picture='" + picture + '\'' +
                ", banner='" + banner + '\'' +
                ", phoneNumber='" + phone_number + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", followersCount=" + followers_count +
                ", followingCount=" + following_count +
                '}';
    }
}