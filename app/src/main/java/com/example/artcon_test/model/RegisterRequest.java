package com.example.artcon_test.model;

import java.util.Date;
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String gender;
    private String phonenumber;
    private Date birthday;
    private String location;
    private String username;
    private String email;
    private String password;

    public RegisterRequest(String firstname, String lastname, String gender, String phonenumber, Date birthday, String location, String username, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.location = location;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", birthday=" + birthday +
                ", location='" + location + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
