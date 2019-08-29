package com.wgu.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wgu.kylerdavisc196project.model.Assessment;
import com.wgu.kylerdavisc196project.model.Course;
import com.wgu.kylerdavisc196project.model.Term;
import com.wgu.kylerdavisc196project.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            TermDbHandler.COURSE_MENTOR_NAME,
            TermDbHandler.COURSE_MENTOR_PHONE,
            TermDbHandler.COURSE_MENTOR_EMAIL,
            TermDbHandler.COURSE_STATUS,
            TermDbHandler.COURSE_TERM_ID
    };

    public static final String[] allAssessmentColumns = {
            TermDbHandler.ASSESSMENT_ID,
            TermDbHandler.ASSESSMENT_NAME,
            TermDbHandler.ASSESSMENT_DUE_DATE,
            TermDbHandler.ASSESSMENT_TYPE,
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
        val.put(TermDbHandler.COURSE_MENTOR_NAME, course.getMentorName());
        val.put(TermDbHandler.COURSE_MENTOR_EMAIL, course.getMentorEmail());
        val.put(TermDbHandler.COURSE_MENTOR_PHONE, course.getMentorPhone());
        val.put(TermDbHandler.COURSE_STATUS, course.getStatus());
        val.put(TermDbHandler.COURSE_TERM_ID, course.getTerm().getId());
        long id = database.insert(TermDbHandler.TABLE_COURSE, null, val);
        return id;
    }
    //Create Assessment
    public long insertAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(TermDbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(TermDbHandler.ASSESSMENT_TYPE, assessment.getType());
        val.put(TermDbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.insert(TermDbHandler.TABLE_ASSESSMENT, null, val);
        return id;
    }
    //Create Note
    public long insertNote(String noteContents, long noteCourseId) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.NOTE_CONTENTS, noteContents);
        val.put(TermDbHandler.NOTE_COURSE_ID, noteCourseId);
        long id = database.insert(TermDbHandler.TABLE_NOTE, null, val);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        String whereClause = "'" + currentDate + "' BETWEEN " + TermDbHandler.TERM_START_DATE + " AND " + TermDbHandler.TERM_END_DATE;
        Cursor cursor = database.query(TermDbHandler.TABLE_TERM,allTermColumns, whereClause, null,null,null,null);
        if (cursor.getCount() != 0) {
            return true;
        }else {
            return false;
        }
    }

    public Term currentTerm() {
        Term term = new Term();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        String whereClause = "'" + currentDate + "' BETWEEN " + TermDbHandler.TERM_START_DATE + " AND " + TermDbHandler.TERM_END_DATE;
        Cursor cursor = database.query(TermDbHandler.TABLE_TERM,allTermColumns, whereClause, null,null,null,null);
        if (cursor != null ) {
            cursor.moveToFirst();
            term.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.TERM_ID)));
            term.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
            term.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE)));
            term.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE)));
        }
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
            cursor.moveToFirst();
            t.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.TERM_ID)));
            t.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
            t.setStartDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE))));
            t.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE))));
        }
        return t;
    }
    //Select All Terms
    public List<Term> selectAllTerms() {
        Cursor cursor = database.query(TermDbHandler.TABLE_TERM,allTermColumns,null,null,null, null, null);
        List<Term> terms =  new ArrayList<>();
        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Term td = new Term();
                td.setId(cursor.getInt((cursor.getColumnIndex(TermDbHandler.TERM_ID))));
                td.setName(cursor.getString((cursor.getColumnIndex(TermDbHandler.TERM_NAME))));
                td.setStartDate(transformedDate(cursor.getString((cursor.getColumnIndex(TermDbHandler.TERM_START_DATE)))));
                td.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE))));
                terms.add(td);
            }
        }
        return terms;
    }
    //Select Single Course
    public Course selectCourse(long id) {
        String rawQuery ="SELECT " +
                "c." + TermDbHandler.COURSE_ID + ", c." + TermDbHandler.COURSE_NAME + ", c." + TermDbHandler.COURSE_DESCRIPTION + ", c." + TermDbHandler.COURSE_START_DATE +
                ", c." + TermDbHandler.COURSE_END_DATE + ", c." + TermDbHandler.COURSE_STATUS + ", c. " + TermDbHandler.COURSE_TERM_ID +
                ", c." + TermDbHandler.COURSE_TERM_ID +  ", c." + TermDbHandler.COURSE_MENTOR_NAME +
                ", c." + TermDbHandler.COURSE_MENTOR_PHONE + ", c." + TermDbHandler.COURSE_MENTOR_EMAIL + ", t." + TermDbHandler.TERM_NAME +
                ", t." + TermDbHandler.TERM_START_DATE + ", t." + TermDbHandler.TERM_END_DATE +
                " FROM " + TermDbHandler.TABLE_COURSE + " c " +
                " INNER JOIN " + TermDbHandler.TABLE_TERM + " t ON c." + TermDbHandler.COURSE_TERM_ID + " = t." + TermDbHandler.TERM_ID  +
                " WHERE c." + TermDbHandler.COURSE_ID + " = " + id;
        Cursor cursor = database.rawQuery(rawQuery, null);
        Course c = new Course();
        Term t = new Term();
        if (cursor != null) {
            cursor.moveToFirst();
            c.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
            c.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
            c.setStartDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE))));
            c.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE))));
            c.setMentorName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_NAME)));
            c.setMentorPhone(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_PHONE)));
            c.setMentorEmail(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_EMAIL)));
            c.setStatus(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS)));
            t.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
            t.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
            t.setStartDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE))));
            t.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE))));
            c.setTerm(t);
        }
        return c;
    }
    //Select All Courses
    public List<Course> selectAllCourses() {
        String rawQuery ="SELECT " +
                "c." + TermDbHandler.COURSE_ID + ", c." + TermDbHandler.COURSE_NAME + ", c." + TermDbHandler.COURSE_DESCRIPTION + ", c." + TermDbHandler.COURSE_START_DATE +
                ", c." + TermDbHandler.COURSE_END_DATE + ", c." + TermDbHandler.COURSE_STATUS +
                ", c." + TermDbHandler.COURSE_TERM_ID + ", c." + TermDbHandler.COURSE_MENTOR_NAME +
                ", c." + TermDbHandler.COURSE_MENTOR_EMAIL + ", c." + TermDbHandler.COURSE_MENTOR_PHONE + ", t." + TermDbHandler.TERM_NAME +
                ", t." + TermDbHandler.TERM_START_DATE + ", t." + TermDbHandler.TERM_END_DATE +
                " FROM " + TermDbHandler.TABLE_COURSE + " c " +
                "INNER JOIN " + TermDbHandler.TABLE_TERM + " t ON c." + TermDbHandler.COURSE_TERM_ID + " = t." + TermDbHandler.TERM_ID;
        Cursor cursor = database.rawQuery(rawQuery, null);
        List<Course> courses =  new ArrayList<>();
        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                Term td = new Term();
                cd.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE)));
                cd.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE)));
                cd.setMentorName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_NAME)));
                cd.setMentorPhone(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_PHONE)));
                cd.setMentorEmail(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_EMAIL)));
                cd.setStatus(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS)));
                td.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
                td.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
                td.setStartDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE)));
                td.setEndDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE)));
                cd.setTerm(td);
                courses.add(cd);
            }
        }
        return courses;
    }
    //Select Courses by Term
    public List<Course> selectCoursesByTerm(int termId) {
        String rawQuery ="SELECT " +
                "c." + TermDbHandler.COURSE_ID + ", c." + TermDbHandler.COURSE_NAME + ", c." + TermDbHandler.COURSE_DESCRIPTION + ", c." + TermDbHandler.COURSE_START_DATE +
                ", c." + TermDbHandler.COURSE_END_DATE + ", c." + TermDbHandler.COURSE_STATUS + ", c. " + TermDbHandler.COURSE_TERM_ID +
                ", c." + TermDbHandler.COURSE_TERM_ID +  ", c." + TermDbHandler.COURSE_MENTOR_NAME +
                ", c." + TermDbHandler.COURSE_MENTOR_EMAIL + ", c." + TermDbHandler.COURSE_MENTOR_PHONE + ", t." + TermDbHandler.TERM_NAME +
                ", t." + TermDbHandler.TERM_START_DATE + ", t." + TermDbHandler.TERM_END_DATE +
                " FROM " + TermDbHandler.TABLE_COURSE + " c " +
                " JOIN " + TermDbHandler.TABLE_TERM + " t ON c." + TermDbHandler.COURSE_TERM_ID + " = t." + TermDbHandler.TERM_ID +
                " WHERE c." + TermDbHandler.COURSE_TERM_ID + " = " + termId;
        Cursor cursor = database.rawQuery(rawQuery,null);
        List<Course> courses =  new ArrayList<>();
        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Course cd = new Course();
                Term td = new Term();
                cd.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_ID)));
                cd.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_NAME)));
                cd.setDescription(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_DESCRIPTION)));
                cd.setStartDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_START_DATE))));
                cd.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_END_DATE))));
                cd.setMentorName(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_NAME)));
                cd.setMentorEmail(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_EMAIL)));
                cd.setMentorPhone(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_MENTOR_PHONE)));
                td.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.COURSE_TERM_ID)));
                td.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_NAME)));
                td.setStartDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_START_DATE))));
                td.setEndDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.TERM_END_DATE))));
                cd.setTerm(td);
                cd.setStatus(cursor.getString(cursor.getColumnIndex(TermDbHandler.COURSE_STATUS)));
                courses.add(cd);
            }
        }
        return courses;
    }
    public Note selectNote(long courseId) {
        Cursor cursor = database.query(TermDbHandler.TABLE_NOTE, new String[]{TermDbHandler.NOTE_ID, TermDbHandler.NOTE_CONTENTS}, TermDbHandler.NOTE_COURSE_ID + "=?", new String[]{String.valueOf(courseId)},null,null,null,null);
        Note n = new Note();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            n.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.NOTE_ID)));
            n.setNoteContents(cursor.getString(cursor.getColumnIndex(TermDbHandler.NOTE_CONTENTS)));
            n.setNoteCourseId((int) (long) courseId);
        }
            return n;
    }
    public boolean assessmentsExistForCourse(long courseId) {
        String whereClause = TermDbHandler.ASSESSMENT_COURSE_ID + " = " + courseId;
        Cursor cursor = database.query(TermDbHandler.TABLE_ASSESSMENT,allAssessmentColumns, whereClause, null,null,null,null);
        if (cursor.getCount() != 0) {
            return true;
        }else {
            return false;
        }
    }
    //Select Assessment
    public Assessment selectAssessment(long id) {
        Cursor cursor = database.query(TermDbHandler.TABLE_ASSESSMENT,allAssessmentColumns, TermDbHandler.ASSESSMENT_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Assessment a = new Assessment();
        a.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_ID)));
        a.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_NAME)));
        a.setType(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_TYPE)  ));
        a.setDueDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_DUE_DATE))));
        a.setCourseId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_COURSE_ID)));
        return a;
    }
    //Select Assessments by Course
    public List<Assessment> selectAssessmentsByCourse(long courseId) {
        Cursor cursor = database.query(TermDbHandler.TABLE_ASSESSMENT,allAssessmentColumns, TermDbHandler.ASSESSMENT_COURSE_ID + "=?",new String[]{String.valueOf(courseId)},null,null,null);
        List<Assessment> assessments =  new ArrayList<>();
        if (cursor.getCount()> 0) {
            while(cursor.moveToNext()) {
                Assessment ad = new Assessment();
                ad.setId(cursor.getInt(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_ID)));
                ad.setName(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_NAME)));
                ad.setType(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_TYPE)));
                ad.setDueDate(transformedDate(cursor.getString(cursor.getColumnIndex(TermDbHandler.ASSESSMENT_DUE_DATE))));
                ad.setCourseId((int) (long)courseId);
                assessments.add(ad);
            }
        }
        return assessments;
    }

    //Update Operations
    //Update Term
    public long updateTerm(Term term) {
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
        val.put(TermDbHandler.COURSE_STATUS, course.getStatus());
        val.put(TermDbHandler.COURSE_MENTOR_NAME, course.getMentorName());
        val.put(TermDbHandler.COURSE_MENTOR_PHONE, course.getMentorPhone());
        val.put(TermDbHandler.COURSE_MENTOR_EMAIL, course.getMentorEmail());
        val.put(TermDbHandler.COURSE_TERM_ID, course.getTerm().getId());
        long id = database.update(TermDbHandler.TABLE_COURSE, val, TermDbHandler.COURSE_ID + "=" + course.getId(), null);
        return id;
    }
    //Update Assessment
    public long updateAssessment(Assessment assessment) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.ASSESSMENT_NAME, assessment.getName());
        val.put(TermDbHandler.ASSESSMENT_DUE_DATE, assessment.getDueDate());
        val.put(TermDbHandler.ASSESSMENT_TYPE, assessment.getType());
        val.put(TermDbHandler.ASSESSMENT_COURSE_ID, assessment.getCourseId());
        long id = database.update(TermDbHandler.TABLE_ASSESSMENT, val, TermDbHandler.ASSESSMENT_ID + "=" + assessment.getId(), null);
        return id;
    }
    //Update Note
    public long updateNote(String noteContents, long noteID) {
        ContentValues val = new ContentValues();
        val.put(TermDbHandler.NOTE_CONTENTS, noteContents);
        long id = database.update(TermDbHandler.TABLE_NOTE, val, TermDbHandler.NOTE_ID + "=" + noteID, null);
        return id;
    }

    //Delete Term
    public long deleteTerm(int termId) {
        long id = database.delete(TermDbHandler.TABLE_TERM, TermDbHandler.TERM_ID + " = " + termId, null);
        return id;
    }
    //Delete Course
    public long deleteCourse(int courseId) {
        long id = database.delete(TermDbHandler.TABLE_COURSE, TermDbHandler.COURSE_ID + " = " + courseId, null);
        return id;
    }
    //Delete Note
    public long deleteNote(long noteId) {
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

        database.delete(TermDbHandler.TABLE_COURSE,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_COURSE+ "'",null);

        database.delete(TermDbHandler.TABLE_TERM,null,null);
        database.delete("sqlite_sequence", "name = '"+ TermDbHandler.TABLE_TERM+ "'",null);
    }
    public String transformedDate(String dateIn) {
        //Date in will be formatted as YYYYMMDD
        return dateIn.substring(4,6) + "/" + dateIn.substring(6,8) + "/" + dateIn.substring(0,4);
        //Date out will be formatted MM/DD/YYYY
    }
    public String dateToDB(String dateIn) {
        //Date in will be formatted as MM/DD/YYY
        //Date out will be formatted as YYYYMMDD
        String[] stringArray = dateIn.split("/");
        String month = stringArray[0];
        String day = stringArray[1];
        if(month.length() == 1) {
            month = "0" + month;
        }
        if(day.length() == 1) {
            day = "0" + day;
        }
        return stringArray[2] + month + day;
    }
    public int[] dateSplits(String dateIn) {
        //For Date picker sets takes in MM/DD/YYYY and puts out an array of [year, month-1(for stupid android datepicker zero base), day]
        int year = Integer.parseInt(dateIn.substring(6,10));
        int month = Integer.parseInt(dateIn.substring(0,2));
        int day = Integer.parseInt(dateIn.substring(3,5));
        int[] dateArray = {year, month-1, day};
        return dateArray;
    }
    public long timeInMillis(String dateIn) {
        //incoming date MM/DD/YYYY
        String allDate = dateIn + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(allDate);
        }
        catch(ParseException e) {
            System.out.println(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }
 }
