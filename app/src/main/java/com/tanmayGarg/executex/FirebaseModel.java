package com.tanmayGarg.executex;

public class FirebaseModel {
    private String title;
    private String description;

    //Empty Constructor
    public FirebaseModel() {
    }

    //Constructor that is being used
    public FirebaseModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //Getters and Setters to achieve abstraction
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}