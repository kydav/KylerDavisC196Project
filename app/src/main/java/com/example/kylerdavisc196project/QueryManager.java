package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.AssessmentType;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Mentor;
import com.example.kylerdavisc196project.model.Status;
import com.example.kylerdavisc196project.model.Term;

import java.util.ArrayList;
import java.util.List;

public class QueryManager {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    public void DBOperations(Context context){
        dbHandler = new DbHandler(context);
    }
    public void open() {
        Log.i("Write Query", "Database Opened");
        database = dbHandler.getWritableDatabase();
    }
    //Term Operations
    private static final String[] allTermColumns = {
            DbHandler.TERM_ID,
            DbHandler.TERM_NAME,
            DbHandler.TERM_START_DATE,
            DbHandler.TERM_END_DATE

    };
    //Create Term
    public long insertTerm(Term term) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.TERM_NAME, term.getName());
        val.put(DbHandler.TERM_START_DATE, term.getStartDate());
        val.put(DbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.insert(DbHandler.TABLE_TERM,null, val);
        return id;
    }
    //Read Term Operations
    public Term selectTerm(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_TERM,allTermColumns,DbHandler.TERM_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Term t = new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        // return Employee
        return t;
    }
    public List<Term> selectAllTerms() {
        Cursor cursor = database.query(DbHandler.TABLE_TERM,allTermColumns,null,null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Term> terms =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Term td = new Term();
                td.setId(cursor.getInt((cursor.getColumnIndex(DbHandler.TERM_ID))));
                td.setName(cursor.getString((cursor.getColumnIndex(DbHandler.TERM_NAME))));
                td.setStartDate(cursor.getString((cursor.getColumnIndex(DbHandler.TERM_START_DATE))));
                terms.add(td);
            }
        }
        return terms;
    }
    //Update Term
    public long update(Term term) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.TERM_NAME, term.getName());
        val.put(DbHandler.TERM_START_DATE, term.getStartDate());
        val.put(DbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.update(DbHandler.TABLE_TERM, val, DbHandler.TERM_ID + " = '" + term.getId() + "'", null);
        return id;
    }
    //Delete Term
    public long delete(Term term) {
        long id = database.delete(DbHandler.TABLE_TERM, DbHandler.TERM_ID + " = " + term.getId(), null);
        return id;
    }

    //Course Operations
    public static final String[] allCourseColumns = {
            DbHandler.COURSE_ID,
            DbHandler.COURSE_NAME,
            DbHandler.COURSE_DESCRIPTION,
            DbHandler.COURSE_START_DATE,
            DbHandler.COURSE_END_DATE,
            DbHandler.COURSE_STATUS_ID,
            DbHandler.COURSE_MENTOR_ID,
            DbHandler.COURSE_TERM_ID
    };
    //Create Course
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
    //Read Operations
    public Term selectCourse(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_COURSE,allTermColumns,DbHandler.COURSE_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Course c = new Course(
                Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7));
        // return Employee
        return c;
    }
    public List<Term> selectAllTerms() {
        Cursor cursor = database.query(DbHandler.TABLE_TERM,allTermColumns,null,null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Term> terms =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Term td = new Term();
                td.setId(cursor.getInt((cursor.getColumnIndex(DbHandler.TERM_ID))));
                td.setName(cursor.getString((cursor.getColumnIndex(DbHandler.TERM_NAME))));
                td.setStartDate(cursor.getString((cursor.getColumnIndex(DbHandler.TERM_START_DATE))));
                terms.add(td);
            }
        }
        return terms;
    }

    //Update
    public long updateCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.COURSE_NAME, course.getName());
        val.put(DbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(DbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(DbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(DbHandler.COURSE_STATUS_ID, course.getStatus().getId());
        val.put(DbHandler.COURSE_MENTOR_ID, course.getMentor().getId());
        val.put(DbHandler.COURSE_TERM_ID, course.getTerm().getId());
        long id = database.update(DbHandler.TABLE_COURSE, val, DbHandler.COURSE_ID + "=" + course.getId(), null);
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
