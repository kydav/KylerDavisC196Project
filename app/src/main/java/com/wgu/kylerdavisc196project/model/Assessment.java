package com.wgu.kylerdavisc196project.model;

public class Assessment {
    int id;
    String name;
    String dueDate;
    String type;
    int courseId;

    public Assessment() {

    }
    public Assessment (String name, String dueDate, String type, int courseId) {
        this.name = name;
        this.dueDate = dueDate;
        this.type = type;
        this.courseId = courseId;
    }

    public Assessment (int id, String name, String dueDate, String type, int courseId) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.type = type;
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

    public void setType (String type) { this.type = type; }

    public void setCourseId (int courseId) {
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

    public String getType() { return this.type; }

    public int getCourseId() {
        return this.courseId;
    }
}
