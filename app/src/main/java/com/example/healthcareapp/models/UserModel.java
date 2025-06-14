package com.example.healthcareapp.models;

public class UserModel {
    public String email;
    public String role;

    public UserModel() {} // Required by Firebase

    public UserModel(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
