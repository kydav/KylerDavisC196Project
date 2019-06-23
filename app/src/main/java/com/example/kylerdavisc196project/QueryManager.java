package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.AssessmentType;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Mentor;
import com.example.kylerdavisc196project.model.Status;
import com.example.kylerdavisc196project.model.Term;

public class QueryManager {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    public void DBOperations(Context context){
        dbHandler = new DbHandler(context);
    }
    public void open() {
        Log.i("Read Query", "Database Opened");
        database = dbHandler.getWritableDatabase();
    }

    public long insertTerm(Term term) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.TERM_NAME, term.getName());
        val.put(DbHandler.TERM_START_DATE, term.getStartDate());
        val.put(DbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.insert(DbHandler.TABLE_TERM,null, val);
        return id;
    }

    public long insertCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.COURSE_NAME, course.getName());
        val.put(DbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(DbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(DbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(DbHandler.COURSE_STATUS_ID, course.getStatus().getId());
        val.put(DbHandler.COURSE_MENTOR_ID, course.getMentor().getId());
        val.put(DbHandler.COURSE_TERM_ID, course.getTerm().getId());
        long id = database.insert(DbHandler.TABLE_COURSE, null, val);
        return id;
    }

    public long insertMentor(Mentor mentor) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.MENTOR_NAME, mentor.getName());
        val.put(DbHandler.MENTOR_PHONE, mentor.getPhone());
        val.put(DbHandler.MENTOR_EMAIL, mentor.getEmail());
        long id = database.insert(DbHandler.TABLE_MENTOR, null, val);
        return id;
    }

    public long insertAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(DbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(DbHandler.ASSESSMENT_TYPE_ID, assessment.getType().getId());
        val.put(DbHandler.ASSESSMENT_COURSE_ID, assessment.getCourse().getId());
        long id = database.insert(DbHandler.TABLE_ASSESSMENT_TYPE, null, val);
        return id;
    }

    public long insertAssessmentType(AssessmentType assessmentType) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ASSESSMENT_TYPE_NAME, assessmentType.getName());
        long id = database.insert(DbHandler.TABLE_ASSESSMENT_TYPE, null, val);
        return id;
    }

    public long insertStatus(Status status) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.STATUS_NAME, status.getName());
        long id = database.insert(DbHandler.TABLE_STATUS, null, val);
        return id;
    }
 }
