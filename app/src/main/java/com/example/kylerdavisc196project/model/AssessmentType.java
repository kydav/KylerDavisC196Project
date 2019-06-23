package com.example.kylerdavisc196project.model;

public class AssessmentType {
    int id;
    String name;

    public AssessmentType (String name) {
        this.name = name;
    }

    public AssessmentType (int id, String name) {
        this.id = id;
        this.name = name;
    }

    //setters
    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    //getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
