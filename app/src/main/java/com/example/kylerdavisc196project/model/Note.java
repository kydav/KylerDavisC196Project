package com.example.kylerdavisc196project.model;

public class Note {
    int id;
    String noteContents;
    int noteCourseId;

    public Note () {    }
    public Note (String noteContents, int noteCourseId) {
        this.noteContents = noteContents;
        this.noteCourseId = noteCourseId;
    }

    public Note (int id, String noteContents, int noteCourseId) {
        this.id = id;
        this.noteContents = noteContents;
        this.noteCourseId = noteCourseId;
    }

    //setters
    public void setId (int id) {
        this.id = id;
    }

    public void setNoteContents (String noteContents) { this.noteContents = noteContents; }

    public void setNoteCourseId (int noteCourseId) { this.noteCourseId = noteCourseId; }

    //getters
    public long getId() {
        return this.id;
    }

    public String getNoteContents() { return this.noteContents; }

    public int getNoteCourseId() { return this.noteCourseId; }
}
