package com.wgu.kylerdavisc196project.model;

public class Course {
    int id;
    String name;
    String description;
    String startDate;
    String endDate;
    String mentorName;
    String mentorPhone;
    String mentorEmail;
    String status;
    Term term;

    public Course() {

    }

    public Course(String name, String description, String startDate, String endDate, String mentorName, String mentorPhone, String mentorEmail, String status, Term term) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.status = status;
        this.term = term;
    }

    public Course(int id, String name, String description, String startDate, String endDate, String mentorName, String mentorPhone, String mentorEmail,  String status, Term term) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.status = status;
        this.term = term;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMentorName(String mentorName) { this.mentorName = mentorName; }

    public void setMentorPhone(String mentorPhone) { this.mentorPhone = mentorPhone; }

    public void setMentorEmail(String mentorEmail) { this.mentorEmail = mentorEmail; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    //getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate () {
        return this.endDate;
    }

    public String getMentorName () { return this.mentorName; }

    public String getMentorPhone () { return this.mentorPhone; }

    public String getMentorEmail () { return this.mentorEmail; }

    public String getStatus () {
        return this.status;
    }

    public Term getTerm () {
        return this.term;
    }
}
