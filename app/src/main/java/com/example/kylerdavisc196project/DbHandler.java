package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.example.kylerdavisc196project.model.Term;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "termTrackerDb";

    //Term Table
    private static final String TABLE_TERM = "term";
    private static final String TERM_ID = "termId";
    private static final String TERM_NAME = "termName";
    private static final String TERM_START_DATE = "termStartDate";
    private static final String TERM_END_DATE = "termEndDate";

    //Term Table Create
    private static final String CREATE_TERM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TERM + " ("
                    + TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TERM_NAME + " TEXT, "
                    + TERM_START_DATE + " DATETIME, "
                    + TERM_END_DATE + " DATETIME" + ")";

    //Term Table Insert
    public long insertTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_NAME, term.getName());
        values.put(TERM_START_DATE, term.getStartDate());
        values.put(TERM_END_DATE, term.getEndDate());

        long termId = db.insert(TABLE_TERM, null, values);

        return termId;
    }

    //Term Table SelectAll
    public List<Term> selectAllTerms() {
        String selectQuery = "SELECT * FROM " + TABLE_TERM;
        List<Term> terms = new ArrayList<>();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Term td = new Term();
                td.setId(c.getInt((c.getColumnIndex(TERM_ID))));
                td.setName(c.getString((c.getColumnIndex(TERM_NAME))));
                td.setStartDate(c.getString((c.getColumnIndex(TERM_START_DATE))));

                terms.add(td);
            } while (c.moveToNext());
        }
        return terms;
    }

    //Term Table Select Term by Id
    public Term selectTermById(int termId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TERM
                + " WHERE " + TERM_ID + " = " + termId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Term td = new Term();
        td.setId(c.getInt((c.getColumnIndex(TERM_ID))));
        td.setName(c.getString((c.getColumnIndex(TERM_NAME))));
        td.setStartDate(c.getString((c.getColumnIndex(TERM_START_DATE))));

        return td;
    }

    //Course Table
    private static final String TABLE_COURSE = "course";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_NAME = "courseName";
    private static final String COURSE_DESCRIPTION = "courseDescription";
    private static final String COURSE_START_DATE = "courseStartDate";
    private static final String COURSE_END_DATE = "courseEndDate";
    private static final String COURSE_MENTOR_ID = "courseMentorId";
    private static final String COURSE_STATUS_ID = "courseStatus";
    private static final String COURSE_TERM_ID = "courseTermId";

    //Course Table Create
    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COURSE + " ("
            + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COURSE_NAME + " TEXT, "
            + COURSE_DESCRIPTION + " TEXT, "
            + COURSE_START_DATE + " DATE, "
            + COURSE_END_DATE + " DATE, "
            + COURSE_MENTOR_ID + " INTEGER, "
            + COURSE_STATUS_ID + " INTEGER, "
            + COURSE_TERM_ID + " INTEGER)";

    //Mentor Table
    private static final String TABLE_MENTOR = "mentor";
    private static final String MENTOR_ID = "mentorId";
    private static final String MENTOR_NAME = "courseMentorName";
    private static final String MENTOR_PHONE = "courseMentorPhone";
    private static final String MENTOR_EMAIL = "courseMentorEmail";

    //Mentor Table Create
    private static final String CREATE_MENTOR_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MENTOR + " ("
            + MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MENTOR_NAME + " TEXT, "
            + MENTOR_PHONE + " TEXT, "
            + MENTOR_EMAIL + " TEXT)";

    //Status Table
    private static final String TABLE_STATUS = "status";
    private static final String STATUS_ID = "statusId";
    private static final String STATUS_NAME = "statusName";

    //Status Table Create
    private  static final String CREATE_STATUS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + " ("
            + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATUS_NAME + " TEXT)";

    //Status Table Setup
    private static final String INSERT_STATUS =
            "INSERT INTO " + TABLE_STATUS + " (" + STATUS_NAME + ") " +
                    "VALUES ('Plan To Take')," +
                    "('In Progress')," +
                    "('Completed')," +
                    "('Dropped')";

    //Assessment Table
    private static final String TABLE_ASSESSMENT = "assessment";
    private static final String ASSESSMENT_ID = "assessmentId";
    private static final String ASSESSMENT_NAME = "assessmentName";
    private static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    private static final String ASSESSMENT_ASSESSMENT_TYPE_ID = "assessmentAssessmentTypeId";
    private static final String ASSESSMENT_COURSE_ID = "assessmentCourseId";

    //Assessment Table Create
    private static final String CREATE_ASSESSMENT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT + " ("
            + ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_NAME + " TEXT, "
            + ASSESSMENT_DUE_DATE + " DATE, "
            + ASSESSMENT_ASSESSMENT_TYPE_ID +  " INTEGER, "
            + ASSESSMENT_COURSE_ID + " INTEGER)";

    //Assessment Type Table
    private static final String TABLE_ASSESSMENT_TYPE = "assessmentType";
    private static final String ASSESSMENT_TYPE_ID = "assessmentTypeId";
    private static final String ASSESSMENT_TYPE_NAME = "assessmentTypeName";

    //Assessment Type Table Create
    private static final String CREATE_ASSESSMENT_TYPE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT_TYPE + " ("
            + ASSESSMENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_TYPE_NAME + " TEXT)";

    ////Assessment Type Table Setup
    private static final String INSERT_ASSESSMENT_TYPE =
            "INSERT INTO " + TABLE_ASSESSMENT_TYPE + " (" + ASSESSMENT_TYPE_NAME + ")" +
                    "VALUES ('Performance')," +
                    "('Objective')";

    //Note Table
    private static final String TABLE_NOTE = "note";
    private static final String NOTE_ID = "noteId";
    private static final String NOTE_CONTENTS = "noteContents";
    private static final String NOTE_COURSE_ID = "noteCourseId";

    //Note Table Create
    private static final String CREATE_NOTE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + " ("
            + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_CONTENTS + " TEXT, "
            + NOTE_COURSE_ID + " INTEGER)";

    //Alert Table
    private static final String TABLE_ALERT = "alert";
    private static final String ALERT_ID = "alertId";
    private static final String ALERT_NAME = "alertName";
    private static final String ALERT_DATE_TIME = "alertDateTime";

    //Alert Table Create
    private static final String CREATE_ALERT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALERT + " ("
            + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALERT_NAME + " TEXT, "
            + ALERT_DATE_TIME + " DATETIME)";

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TERM_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_MENTOR_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
        db.execSQL(CREATE_ASSESSMENT_TABLE);
        db.execSQL(CREATE_ASSESSMENT_TYPE_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_ALERT_TABLE);
        db.execSQL(INSERT_STATUS);
        db.execSQL(INSERT_ASSESSMENT_TYPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERT);
        onCreate(db);
    }
}
