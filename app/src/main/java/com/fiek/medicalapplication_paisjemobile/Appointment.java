package com.fiek.medicalapplication_paisjemobile;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String date;

    // Constructor with ID, title, description, and date
    public Appointment(int id, String title, String description, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}