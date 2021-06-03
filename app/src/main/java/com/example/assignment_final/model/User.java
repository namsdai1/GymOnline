package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

public class User {
    private String IDUser;
    private String userName, password, fullName, address, phoneNumber;
    private String images;


    public User(String IDUser, String fullName, String address, String phoneNumber) {
        this.IDUser = IDUser;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public User(String userName, String password, String fullName, String address, String phoneNumber, String images) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.images = images;
    }

    public User() {
    }
    @Exclude
    public String getIDUser() {
        return IDUser;
    }

    @Exclude
    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "User{" +
                "IDUser='" + IDUser + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
