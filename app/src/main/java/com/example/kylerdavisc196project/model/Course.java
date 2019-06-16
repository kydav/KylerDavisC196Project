package com.example.kylerdavisc196project.model;

import java.sql.Date;

public class Course {
    int id;
    String name;
    Date startDate;
    Date endDate;
    String status;
    Term term;
    Mentor mentor;

    public Course(String name, Date startDate, Date endDate, String status, Term term, Mentor mentor) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.term = term;
        this.mentor = mentor;
    }

    public Course(int id, String name, Date startDate, Date endDate, String status, Term term, Mentor mentor) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.term = term;
        this.mentor = mentor;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    //getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate () {
        return this.endDate;
    }

    public String getStatus () {
        return this.status;
    }

    public Term getTerm () {
        return this.term;
    }

    public Mentor getMentor () {
        return this.mentor;
    }
}
