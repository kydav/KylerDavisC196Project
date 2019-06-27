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

    private static final String[] allTermColumns = {
            DbHandler.TERM_ID,
            DbHandler.TERM_NAME,
            DbHandler.TERM_START_DATE,
            DbHandler.TERM_END_DATE
    };
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
    public static final String[] allMentorColumns = {
            DbHandler.MENTOR_ID,
            DbHandler.MENTOR_NAME,
            DbHandler.MENTOR_EMAIL,
            DbHandler.MENTOR_EMAIL
    };
    public static final String[] allStatusColumns = {
            DbHandler.STATUS_ID,
            DbHandler.STATUS_NAME
    };
    public static final String[] allAssessmentColumns = {
            DbHandler.ASSESSMENT_ID,
            DbHandler.ASSESSMENT_NAME,
            DbHandler.ASSESSMENT_DUE_DATE,
            DbHandler.ASSESSMENT_ASSESSMENT_TYPE_ID,
            DbHandler.ASSESSMENT_COURSE_ID
    };

    //Create Operations
    //Create Term
    public long insertTerm(Term term) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.TERM_NAME, term.getName());
        val.put(DbHandler.TERM_START_DATE, term.getStartDate());
        val.put(DbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.insert(DbHandler.TABLE_TERM,null, val);
        return id;
    }
    //Create Course
    public long insertCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.COURSE_NAME, course.getName());
        val.put(DbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(DbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(DbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(DbHandler.COURSE_STATUS_ID, course.getStatus());
        val.put(DbHandler.COURSE_MENTOR_ID, course.getMentor());
        val.put(DbHandler.COURSE_TERM_ID, course.getTerm());
        long id = database.insert(DbHandler.TABLE_COURSE, null, val);
        return id;
    }
    //Create Mentor
    public long insertMentor(Mentor mentor) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.MENTOR_NAME, mentor.getName());
        val.put(DbHandler.MENTOR_PHONE, mentor.getPhone());
        val.put(DbHandler.MENTOR_EMAIL, mentor.getEmail());
        long id = database.insert(DbHandler.TABLE_MENTOR, null, val);
        return id;
    }
    //Create Assessment
    public long insertAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(DbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(DbHandler.ASSESSMENT_TYPE_ID, assessment.getTypeId());
        val.put(DbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.insert(DbHandler.TABLE_ASSESSMENT_TYPE, null, val);
        return id;
    }
    //Create Assessment Type
    public long insertAssessmentType(AssessmentType assessmentType) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ASSESSMENT_TYPE_NAME, assessmentType.getName());
        long id = database.insert(DbHandler.TABLE_ASSESSMENT_TYPE, null, val);
        return id;
    }
    //Create Status
    public long insertStatus(Status status) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.STATUS_NAME, status.getName());
        long id = database.insert(DbHandler.TABLE_STATUS, null, val);
        return id;
    }
    //Create Note
    public long insertNote(String noteContents, int noteCourseId) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.NOTE_CONTENTS, noteContents);
        val.put(DbHandler.NOTE_COURSE_ID, noteCourseId);
        long id = database.insert(DbHandler.TABLE_NOTE, null, val);
        return id;
    }
    //Create Alert
    public long insertAlert(String alertName, String alertDateTime) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ALERT_NAME, alertName);
        val.put(DbHandler.ALERT_DATE_TIME, alertDateTime);
        long id = database.insert(DbHandler.TABLE_ALERT, null, val);
        return id;
    }

    //Read Operations
    //Select Single Term
    public Term selectTerm(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_TERM,allTermColumns,DbHandler.TERM_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//        Term t = new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        Term t = new Term();
        if (cursor != null) {
            t.setId(cursor.getInt(cursor.getColumnIndex(DbHandler.TERM_ID)));
            t.setName(cursor.getString(cursor.getColumnIndex(DbHandler.TERM_NAME)));
            t.setStartDate(cursor.getString(cursor.getColumnIndex(DbHandler.TERM_START_DATE)));
            t.setEndDate(cursor.getString(cursor.getColumnIndex(DbHandler.TERM_END_DATE)));
        }
        return t;
    }
    //Select All Terms
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
    //Select Single Course
    public Course selectCourse(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_COURSE,allTermColumns,DbHandler.COURSE_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//        Course c = new Course(
//                Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getString(4),
//                cursor.getInt(5),
//                cursor.getInt(6),
//                cursor.getInt(7));
        Course c = new Course();
        if (cursor != null) {
            c.setName(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_NAME)));
            c.setDescription(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_DESCRIPTION)));
            c.setStartDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_START_DATE)));
            c.setEndDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_END_DATE)));
            c.setStatus(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_STATUS_ID)));
            c.setTerm(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_TERM_ID)));
            c.setMentor(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_MENTOR_ID)));
        }
        return c;
    }
    //Select All Courses
    public List<Course> selectAllCourses() {
        Cursor cursor = database.query(DbHandler.TABLE_COURSE,allCourseColumns,null,null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Course> courses =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                cd.setId(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_START_DATE)));
                cd.setEndDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_END_DATE)));
                cd.setStatus(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_STATUS_ID)));
                cd.setTerm(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_TERM_ID)));
                cd.setMentor(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_MENTOR_ID)));
                courses.add(cd);
            }
        }
        return courses;
    }
    //Select Courses by Term
    public List<Course> selectCoursesByTerm(int termId) {
        Cursor cursor = database.query(DbHandler.TABLE_COURSE,allCourseColumns,DbHandler.COURSE_TERM_ID + "=?",new String[]{String.valueOf(termId)},null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Course> courses =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                cd.setId(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_START_DATE)));
                cd.setEndDate(cursor.getString(cursor.getColumnIndex(DbHandler.COURSE_END_DATE)));
                cd.setStatus(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_STATUS_ID)));
                cd.setTerm(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_TERM_ID)));
                cd.setMentor(cursor.getInt(cursor.getColumnIndex(DbHandler.COURSE_MENTOR_ID)));
                courses.add(cd);
            }
        }
        return courses;
    }
    //Select Mentor
    public Term selectMentor(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_MENTOR,allTermColumns,DbHandler.TERM_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Term t = new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return t;
    }
    //Select Status
    public Status selectStatus(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_STATUS,allStatusColumns,DbHandler.STATUS_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Status s = new Status(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        return s;
    }
    //Select Assessment
    public Assessment selectAssessment(int id) {
        Cursor cursor = database.query(DbHandler.TABLE_ASSESSMENT,allAssessmentColumns, DbHandler.ASSESSMENT_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        Assessment a = new Assessment();
        //TODO FINISH WRITING THIS GUY OUT
        return a;
    }

    //Update Operations
    //Update Term
    public long update(Term term) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.TERM_NAME, term.getName());
        val.put(DbHandler.TERM_START_DATE, term.getStartDate());
        val.put(DbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.update(DbHandler.TABLE_TERM, val, DbHandler.TERM_ID + " = '" + term.getId() + "'", null);
        return id;
    }
    //Update Course
    public long updateCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.COURSE_NAME, course.getName());
        val.put(DbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(DbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(DbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(DbHandler.COURSE_STATUS_ID, course.getStatus());
        val.put(DbHandler.COURSE_MENTOR_ID, course.getMentor());
        val.put(DbHandler.COURSE_TERM_ID, course.getTerm());
        long id = database.update(DbHandler.TABLE_COURSE, val, DbHandler.COURSE_ID + "=" + course.getId(), null);
        return id;
    }
    //Update Mentor
    public long updateMentor(Mentor mentor) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.MENTOR_NAME, mentor.getName());
        val.put(DbHandler.MENTOR_EMAIL, mentor.getEmail());
        val.put(DbHandler.MENTOR_PHONE, mentor.getPhone());
        long id = database.update(DbHandler.TABLE_MENTOR, val, DbHandler.MENTOR_ID + "=" + mentor.getId(), null);
        return id;
    }
    //Update Assessment
    public long updateAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(DbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(DbHandler.ASSESSMENT_TYPE_ID, assessment.getTypeId());
        val.put(DbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.update(DbHandler.TABLE_ASSESSMENT, val, DbHandler.ASSESSMENT_ID + "=" + assessment.getId(), null);
        return id;
    }
    //Update Note
    public long updateNote(String noteContents, int noteID) {
        ContentValues val = new ContentValues();
        val.put(DbHandler.NOTE_CONTENTS, noteContents);
        long id = database.update(DbHandler.TABLE_NOTE, val, DbHandler.NOTE_ID + "=" + noteID, null);
        return id;
    }




    //Delete Term
    public long delete(Term term) {
        long id = database.delete(DbHandler.TABLE_TERM, DbHandler.TERM_ID + " = " + term.getId(), null);
        return id;
    }
    //Delete Course
    public long deleteCourse(Course course) {
        long id = database.delete(DbHandler.TABLE_TERM, DbHandler.COURSE_ID + " = " + course.getId(), null);
        return id;
    }
    //Delete Mentor
    public long deleteMentor(Mentor mentor) {
        long id = database.delete(DbHandler.TABLE_MENTOR, DbHandler.MENTOR_ID + " = " + mentor.getId(), null);
        return id;
    }
    //Delete Note
    public long deleteNote(int noteId) {
        long id = database.delete(DbHandler.TABLE_NOTE, DbHandler.NOTE_ID + "=" + noteId, null);
        return id;
    }
    //Delete Assessment
    public long deleteAssessment(int assessmentId) {
        long id = database.delete(DbHandler.TABLE_ASSESSMENT, DbHandler.ASSESSMENT_ID + "=" + assessmentId, null);
        return id;
    }


 }
