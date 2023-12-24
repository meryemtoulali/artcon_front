package com.example.artcon_test.model;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.Date;
import java.util.List;

import retrofit2.http.Multipart;

public class UpdateUserViewModel extends ViewModel {
    private String bio;
    private String firstname;
    private String lastname;
    private String location;
    private String gender;
    private String phone_number;
    private String title;
    private String type;
    private String username;
    private File picture;
    private Multipart banner;
    private Date birthday;
    private List<Interest> interests;
    private List<Long> interestIds;

    public UpdateUserViewModel(String bio, String firstname, String lastname, String location, String gender, String phone_number, String title, String type, String username, File picture, Multipart banner, Date birthday) {
        this.bio = bio;
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.gender = gender;
        this.phone_number = phone_number;
        this.title = title;
        this.type = type;
        this.username = username;
        this.picture = picture;
        this.banner = banner;
        this.birthday = birthday;
    }

    public UpdateUserViewModel() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public Multipart getBanner() {
        return banner;
    }

    public void setBanner(Multipart banner) {
        this.banner = banner;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Long> getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(List<Long> interestIds) {
        this.interestIds = interestIds;
    }
}
