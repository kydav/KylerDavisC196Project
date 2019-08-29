package com.wgu.kylerdavisc196project;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TermDbHandler extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "termTrackerDb";

    //Term Table
    public static final String TABLE_TERM = "term";
    public static final String TERM_ID = "termId";
    public static final String TERM_NAME = "termName";
    public static final String TERM_START_DATE = "termStartDate";
    public static final String TERM_END_DATE = "termEndDate";

    //Term Table Create
    private static final String CREATE_TERM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TERM + " ("
                    + TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TERM_NAME + " TEXT, "
                    + TERM_START_DATE + " DATETIME, "
                    + TERM_END_DATE + " DATETIME)";

    //Course Table
    public static final String TABLE_COURSE = "course";
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_DESCRIPTION = "courseDescription";
    public static final String COURSE_START_DATE = "courseStartDate";
    public static final String COURSE_END_DATE = "courseEndDate";
    public static final String COURSE_MENTOR_NAME = "courseMentorName";
    public static final String COURSE_MENTOR_PHONE = "courseMentorPhone";
    public static final String COURSE_MENTOR_EMAIL = "courseMentorEmail";
    public static final String COURSE_STATUS = "courseStatus";
    public static final String COURSE_TERM_ID = "courseTermId";

    //Course Table Create
    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COURSE + " ("
            + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COURSE_NAME + " TEXT, "
            + COURSE_DESCRIPTION + " TEXT, "
            + COURSE_START_DATE + " DATE, "
            + COURSE_END_DATE + " DATE, "
            + COURSE_MENTOR_NAME + " TEXT, "
            + COURSE_MENTOR_PHONE + " TEXT, "
            + COURSE_MENTOR_EMAIL + " TEXT, "
            + COURSE_STATUS + " TEXT, "
            + COURSE_TERM_ID + " INTEGER,"
            + "FOREIGN KEY ("+COURSE_TERM_ID+") REFERENCES " + TABLE_TERM + "("+TERM_ID+") ON DELETE CASCADE)";

    //Assessment Table
    public static final String TABLE_ASSESSMENT = "assessment";
    public static final String ASSESSMENT_ID = "assessmentId";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_COURSE_ID = "assessmentCourseId";

    //Assessment Table Create
    private static final String CREATE_ASSESSMENT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT + " ("
            + ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_NAME + " TEXT, "
            + ASSESSMENT_DUE_DATE + " DATE, "
            + ASSESSMENT_TYPE +  " TEXT, "
            + ASSESSMENT_COURSE_ID + " INTEGER,"
            + "FOREIGN KEY ("+ASSESSMENT_COURSE_ID+") REFERENCES " + TABLE_COURSE + "("+COURSE_ID+") ON DELETE CASCADE)";

    //Note Table
    public static final String TABLE_NOTE = "note";
    public static final String NOTE_ID = "noteId";
    public static final String NOTE_CONTENTS = "noteContents";
    public static final String NOTE_COURSE_ID = "noteCourseId";

    //Note Table Create
    private static final String CREATE_NOTE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + " ("
            + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_CONTENTS + " TEXT, "
            + NOTE_COURSE_ID + " INTEGER,"
            + "FOREIGN KEY ("+NOTE_COURSE_ID+") REFERENCES " + TABLE_COURSE + "("+COURSE_ID+") ON DELETE CASCADE)";

    public TermDbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TERM_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
            db.execSQL(CREATE_ASSESSMENT_TABLE);
            db.execSQL(CREATE_NOTE_TABLE);
        } catch (SQLException e) {
            System.out.println("Error creating DB" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }
}