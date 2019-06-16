package com.example.kylerdavisc196project.model;

public class Mentor {
    int id;
    String name;
    String phone;
    String email;

    public Mentor (String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Mentor (int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    //getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }
}
