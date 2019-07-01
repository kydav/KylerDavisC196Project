package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.AssessmentType;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Mentor;
import com.example.kylerdavisc196project.model.Status;
import com.example.kylerdavisc196project.model.Term;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QueryManager {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;
    private static final String[] allTermColumns = {
            TermDbHandler.TERM_ID,
            TermDbHandler.TERM_NAME,
            TermDbHandler.TERM_START_DATE,
            TermDbHandler.TERM_END_DATE
    };
    public static final String[] allCourseColumns = {
            TermDbHandler.COURSE_ID,
            TermDbHandler.COURSE_NAME,
            TermDbHandler.COURSE_DESCRIPTION,
            TermDbHandler.COURSE_START_DATE,
            TermDbHandler.COURSE_END_DATE,
            TermDbHandler.COURSE_STATUS_ID,
            TermDbHandler.COURSE_MENTOR_ID,
            TermDbHandler.COURSE_TERM_ID
    };
    public static final String[] allMentorColumns = {
            TermDbHandler.MENTOR_ID,
            TermDbHandler.MENTOR_NAME,
            TermDbHandler.MENTOR_EMAIL,
            TermDbHandler.MENTOR_EMAIL
    };
    public static final String[] allStatusColumns = {
            TermDbHandler.STATUS_ID,
            TermDbHandler.STATUS_NAME
    };
    public static final String[] allAssessmentColumns = {
            TermDbHandler.ASSESSMENT_ID,
            TermDbHandler.ASSESSMENT_NAME,
            TermDbHandler.ASSESSMENT_DUE_DATE,
            TermDbHandler.ASSESSMENT_ASSESSMENT_TYPE_ID,
            TermDbHandler.ASSESSMENT_COURSE_ID
    };
    public QueryManager(Context context){
        dbHandler = new TermDbHandler(context);
    }
    public void open() {
        Log.i("Write Query", "Database Opened");
        database = dbHandler.getWritableDatabase();
    }
    public void close() {
        Log.i("Write Query", "Database Closed");
        dbHandler.close();
    }
    //Create Operations
    //Create Term
    public long insertTerm(Term term) {
        long id = -1;
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.TERM_NAME, term.getName());
        val.put(TermDbHandler.TERM_START_DATE, term.getStartDate());
        val.put(TermDbHandler.TERM_END_DATE, term.getEndDate());
        try {
            id = database.insert(TermDbHandler.TABLE_TERM, null, val);
        }catch(Exception e) {
            System.out.println(e);
        }
        return id;
    }
    //Create Course
    public long insertCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.COURSE_NAME, course.getName());
        val.put(TermDbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(TermDbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(TermDbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(TermDbHandler.COURSE_STATUS_ID, course.getStatus());
        val.put(TermDbHandler.COURSE_MENTOR_ID, course.getMentor());
        val.put(TermDbHandler.COURSE_TERM_ID, course.getTerm());
        long id = database.insert(TermDbHandler.TABLE_COURSE, null, val);
        return id;
    }
    //Create Mentor
    public long insertMentor(Mentor mentor) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.MENTOR_NAME, mentor.getName());
        val.put(TermDbHandler.MENTOR_PHONE, mentor.getPhone());
        val.put(TermDbHandler.MENTOR_EMAIL, mentor.getEmail());
        long id = database.insert(TermDbHandler.TABLE_MENTOR, null, val);
        return id;
    }
    //Create Assessment
    public long insertAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(TermDbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(TermDbHandler.ASSESSMENT_ASSESSMENT_TYPE_ID, assessment.getTypeId());
        val.put(TermDbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.insert(TermDbHandler.TABLE_ASSESSMENT, null, val);
        return id;
    }
    //Create Assessment Type
    public long insertAssessmentType(AssessmentType assessmentType) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ASSESSMENT_TYPE_NAME, assessmentType.getName());
        long id = database.insert(TermDbHandler.TABLE_ASSESSMENT_TYPE, null, val);
        return id;
    }
    //Create Status
    public long insertStatus(Status status) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.STATUS_NAME, status.getName());
        long id = database.insert(TermDbHandler.TABLE_STATUS, null, val);
        return id;
    }
    //Create Note
    public long insertNote(String noteContents, int noteCourseId) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.NOTE_CONTENTS, noteContents);
        val.put(TermDbHandler.NOTE_COURSE_ID, noteCourseId);
        long id = database.insert(TermDbHandler.TABLE_NOTE, null, val);
        return id;
    }
    //Create Alert
    public long insertAlert(String alertName, String alertDateTime) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ALERT_NAME, alertName);
        val.put(TermDbHandler.ALERT_DATE_TIME, alertDateTime);
        long id = database.insert(TermDbHandler.TABLE_ALERT, null, val);
        return id;
    }

    //Read Operations
    //See if data in term
    public boolean isTermEmpty() {
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(database,TermDbHandler.TABLE_TERM);
        if (NoOfRows == 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean currentTermExists() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        return true;
    }
    public Term currentTerm() {
        Term term = new Term();

        return term;
    }

    //Select Single Term
    public Term selectTerm(int id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_TERM,allTermColumns, TermDbHandler.TERM_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
    //  if (cursor != null)
    //  cursor.moveToFirst();
    //  Term t = new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        Term t = new Term();
        if (cursor != null) {
            t.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.TERM_ID)));
            t.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
            t.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE)));
            t.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE)));
        }
        return t;
    }
    //Select All Terms
    public List<Term> selectAllTerms() {
        Cursor cursor = database.query(TermDbHandler.TABLE_TERM,allTermColumns,null,null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Term> terms =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Term td = new Term();
                td.setId(cursor.getInt((cursor.getColumnIndex(TermDbHandler.TERM_ID))));
                td.setName(cursor.getString((cursor.getColumnIndex(TermDbHandler.TERM_NAME))));
                td.setStartDate(cursor.getString((cursor.getColumnIndex(TermDbHandler.TERM_START_DATE))));
                terms.add(td);
            }
        }
        return terms;
    }
    //Select Single Course
    public Course selectCourse(int id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_COURSE,allTermColumns, TermDbHandler.COURSE_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
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
            c.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
            c.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
            c.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE)));
            c.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE)));
            c.setStatus(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS_ID)));
            c.setTerm(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
            c.setMentor(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_ID)));
        }
        return c;
    }
    //Select All Courses
    public List<Course> selectAllCourses() {
        Cursor cursor = database.query(TermDbHandler.TABLE_COURSE,allCourseColumns,null,null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Course> courses =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                cd.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE)));
                cd.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE)));
                cd.setStatus(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS_ID)));
                cd.setTerm(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
                cd.setMentor(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_ID)));
                courses.add(cd);
            }
        }
        return courses;
    }
    //Select Courses by Term
    public List<Course> selectCoursesByTerm(int termId) {
        Cursor cursor = database.query(TermDbHandler.TABLE_COURSE,allCourseColumns, TermDbHandler.COURSE_TERM_ID + "=?",new String[]{String.valueOf(termId)},null,null, null, null);
        // Log.e(LOG, selectQuery);
        List<Course> courses =  new ArrayList<>();

        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                cd.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE)));
                cd.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE)));
                cd.setStatus(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS_ID)));
                cd.setTerm(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
                cd.setMentor(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_ID)));
                courses.add(cd);
            }
        }
        return courses;
    }
    //Select Mentor
    public Term selectMentor(int id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_MENTOR,allTermColumns, TermDbHandler.TERM_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Term t = new Term(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return t;
    }
    //Select Status
    public Status selectStatus(int id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_STATUS,allStatusColumns, TermDbHandler.STATUS_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Status s = new Status(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        return s;
    }
    //Select Assessment
    public Assessment selectAssessment(int id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_ASSESSMENT,allAssessmentColumns, TermDbHandler.ASSESSMENT_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
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
        val.put(TermDbHandler.TERM_NAME, term.getName());
        val.put(TermDbHandler.TERM_START_DATE, term.getStartDate());
        val.put(TermDbHandler.TERM_END_DATE, term.getEndDate());
        long id = database.update(TermDbHandler.TABLE_TERM, val, TermDbHandler.TERM_ID + " = '" + term.getId() + "'", null);
        return id;
    }
    //Update Course
    public long updateCourse(Course course) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.COURSE_NAME, course.getName());
        val.put(TermDbHandler.COURSE_DESCRIPTION, course.getDescription());
        val.put(TermDbHandler.COURSE_START_DATE, course.getStartDate());
        val.put(TermDbHandler.COURSE_END_DATE, course.getEndDate());
        val.put(TermDbHandler.COURSE_STATUS_ID, course.getStatus());
        val.put(TermDbHandler.COURSE_MENTOR_ID, course.getMentor());
        val.put(TermDbHandler.COURSE_TERM_ID, course.getTerm());
        long id = database.update(TermDbHandler.TABLE_COURSE, val, TermDbHandler.COURSE_ID + "=" + course.getId(), null);
        return id;
    }
    //Update Mentor
    public long updateMentor(Mentor mentor) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.MENTOR_NAME, mentor.getName());
        val.put(TermDbHandler.MENTOR_EMAIL, mentor.getEmail());
        val.put(TermDbHandler.MENTOR_PHONE, mentor.getPhone());
        long id = database.update(TermDbHandler.TABLE_MENTOR, val, TermDbHandler.MENTOR_ID + "=" + mentor.getId(), null);
        return id;
    }
    //Update Assessment
    public long updateAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(TermDbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(TermDbHandler.ASSESSMENT_TYPE_ID, assessment.getTypeId());
        val.put(TermDbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.update(TermDbHandler.TABLE_ASSESSMENT, val, TermDbHandler.ASSESSMENT_ID + "=" + assessment.getId(), null);
        return id;
    }
    //Update Note
    public long updateNote(String noteContents, int noteID) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.NOTE_CONTENTS, noteContents);
        long id = database.update(TermDbHandler.TABLE_NOTE, val, TermDbHandler.NOTE_ID + "=" + noteID, null);
        return id;
    }




    //Delete Term
    public long delete(Term term) {
        long id = database.delete(TermDbHandler.TABLE_TERM, TermDbHandler.TERM_ID + " = " + term.getId(), null);
        return id;
    }
    //Delete Course
    public long deleteCourse(Course course) {
        long id = database.delete(TermDbHandler.TABLE_TERM, TermDbHandler.COURSE_ID + " = " + course.getId(), null);
        return id;
    }
    //Delete Mentor
    public long deleteMentor(Mentor mentor) {
        long id = database.delete(TermDbHandler.TABLE_MENTOR, TermDbHandler.MENTOR_ID + " = " + mentor.getId(), null);
        return id;
    }
    //Delete Note
    public long deleteNote(int noteId) {
        long id = database.delete(TermDbHandler.TABLE_NOTE, TermDbHandler.NOTE_ID + "=" + noteId, null);
        return id;
    }
    //Delete Assessment
    public long deleteAssessment(int assessmentId) {
        long id = database.delete(TermDbHandler.TABLE_ASSESSMENT, TermDbHandler.ASSESSMENT_ID + "=" + assessmentId, null);
        return id;
    }

    public void deleteData() {
        database.delete(TermDbHandler.TABLE_ASSESSMENT,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_ASSESSMENT+ "'",null);

        database.delete(TermDbHandler.TABLE_NOTE,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_NOTE+ "'",null);

        database.delete(TermDbHandler.TABLE_MENTOR,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_MENTOR+ "'",null);

        database.delete(TermDbHandler.TABLE_ALERT,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_ALERT+ "'",null);

        database.delete(TermDbHandler.TABLE_COURSE,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_COURSE+ "'",null);

        database.delete(TermDbHandler.TABLE_TERM,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_TERM+ "'",null);
    }
 }
