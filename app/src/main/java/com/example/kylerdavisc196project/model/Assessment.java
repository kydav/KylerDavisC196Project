package com.example.kylerdavisc196project.model;

import java.sql.Date;

public class Assessment {
    int id;
    String name;
    String dueDate;
    int typeId;
    int courseId;

    public Assessment() {

    }
    public Assessment (String name, String dueDate, int courseId, int typeId) {
        this.name = name;
        this.dueDate = dueDate;
        this.typeId = typeId;
        this.courseId = courseId;
    }

    public Assessment (int id, String name, String dueDate, int courseId, int typeId) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.typeId = typeId;
        this.courseId = courseId;
    }

    //setters
    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setDueDate (String dueDate) {
        this.dueDate = dueDate;
    }

    public void setType (int type) { this.typeId = typeId; }

    public void setCourse () {
        this.courseId = courseId;
    }

    //getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public int getTypeId() { return this.typeId; }

    public int getCourseId() {
        return this.courseId;
    }
}
