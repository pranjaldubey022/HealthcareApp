package com.example.healthcareapp.models;

public class Doctor {
    private String name;
    private String specialization;

    public Doctor() {} // Needed for Firebase

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
}
