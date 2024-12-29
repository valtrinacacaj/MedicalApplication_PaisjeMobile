package com.fiek.medicalapplication_paisjemobile;

public class Medication {
    private String name;        // Emri i medikamentit
    private String dosage;      // Doza e medikamentit
    private String scheduler;    // Orari i përdorimit të medikamentit

    public Medication(String name, String dosage, String scheduler) {
        this.name = name;
        this.dosage = dosage;
        this.scheduler = scheduler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }
}
