package com.example.healthcareapp.models;

import java.util.List;

public class Hospital {
    private String id;
    private String name;
    private String address;
    private List<String> facilities;
    private List<Doctor> doctors;

    // Required empty constructor for Firebase
    public Hospital(String name, String address) {
    }

    // Constructor without doctors/facilities
    public Hospital(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Constructor with all fields
    public Hospital(String id, String name, String address, List<String> facilities, List<Doctor> doctors) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.facilities = facilities;
        this.doctors = doctors;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<String> getFacilities() { return facilities; }
    public List<Doctor> getDoctors() { return doctors; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
}
