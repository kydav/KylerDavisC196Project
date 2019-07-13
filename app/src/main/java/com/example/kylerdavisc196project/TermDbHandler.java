package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kylerdavisc196project.model.AssessmentType;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Status;
import com.example.kylerdavisc196project.model.Term;

import java.util.ArrayList;
import java.util.List;

public class TermDbHandler extends SQLiteOpenHelper {
    //TODO REFACTOR DATABASE, NEED TO REMOVE AND CHANGE SOME FKs FK IS A REFERENCE FROM THE CHILD TABLE TO PARENT TABLE ID
    //TODO THE WAY I DID THINGS I'M CAUSING REFERENTIAL ISSUES WHEN A MENTOR IS DELETED.
    private static final String LOG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "termTrackerDb";

    public static final Status statusPlanToTake = new Status(1, "Plan To Take");
    public static final Status statusInProgress = new Status(2, "In Progress");
    public static final Status statusCompleted = new Status(3, "Completed");
    public static final Status statusDropped = new Status(4, "Dropped");
    public static final AssessmentType assessTypePerformance = new AssessmentType(1, "Performance");
    public static final AssessmentType assessTypeObjective = new AssessmentType(2, "Objective");


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

    //Mentor Table
    public static final String TABLE_MENTOR = "mentor";
    public static final String MENTOR_ID = "mentorId";
    public static final String MENTOR_NAME = "courseMentorName";
    public static final String MENTOR_PHONE = "courseMentorPhone";
    public static final String MENTOR_EMAIL = "courseMentorEmail";

    //Mentor Table Create
    private static final String CREATE_MENTOR_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MENTOR + " ("
                    + MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MENTOR_NAME + " TEXT, "
                    + MENTOR_PHONE + " TEXT, "
                    + MENTOR_EMAIL + " TEXT)";
    //Status Table
    public static final String TABLE_STATUS = "status";
    public static final String STATUS_ID = "statusId";
    public static final String STATUS_NAME = "statusName";

    //Status Table Create
    private  static final String CREATE_STATUS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + " ("
                    + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATUS_NAME + " TEXT)";

    //Course Table
    public static final String TABLE_COURSE = "course";
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_DESCRIPTION = "courseDescription";
    public static final String COURSE_START_DATE = "courseStartDate";
    public static final String COURSE_END_DATE = "courseEndDate";
    public static final String COURSE_MENTOR_ID = "courseMentorId";
    public static final String COURSE_STATUS_ID = "courseStatus";
    public static final String COURSE_TERM_ID = "courseTermId";

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
            + COURSE_TERM_ID + " INTEGER,"
            + "FOREIGN KEY ("+COURSE_MENTOR_ID+") REFERENCES " + TABLE_MENTOR + "("+MENTOR_ID+") ON DELETE CASCADE,"
            + "FOREIGN KEY ("+COURSE_STATUS_ID+") REFERENCES " + TABLE_STATUS + "("+STATUS_ID+") ON DELETE CASCADE,"
            + "FOREIGN KEY ("+COURSE_TERM_ID+") REFERENCES " + TABLE_COURSE + "("+TERM_ID+") ON DELETE CASCADE)";
    //Assessment Type Table
    public static final String TABLE_ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_TYPE_ID = "assessmentTypeId";
    public static final String ASSESSMENT_TYPE_NAME = "assessmentTypeName";

    //Assessment Type Table Create
    private static final String CREATE_ASSESSMENT_TYPE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT_TYPE + " ("
                    + ASSESSMENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ASSESSMENT_TYPE_NAME + " TEXT)";

    //Assessment Table
    public static final String TABLE_ASSESSMENT = "assessment";
    public static final String ASSESSMENT_ID = "assessmentId";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    public static final String ASSESSMENT_ASSESSMENT_TYPE_ID = "assessmentAssessmentTypeId";
    public static final String ASSESSMENT_COURSE_ID = "assessmentCourseId";

    //Assessment Table Create
    private static final String CREATE_ASSESSMENT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT + " ("
            + ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_NAME + " TEXT, "
            + ASSESSMENT_DUE_DATE + " DATE, "
            + ASSESSMENT_ASSESSMENT_TYPE_ID +  " INTEGER, "
            + ASSESSMENT_COURSE_ID + " INTEGER,"
            + "FOREIGN KEY ("+ASSESSMENT_ASSESSMENT_TYPE_ID+") REFERENCES " + TABLE_ASSESSMENT_TYPE + "("+ASSESSMENT_TYPE_ID+") ON DELETE CASCADE,"
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

    //Alert Table
    public static final String TABLE_ALERT = "alert";
    public static final String ALERT_ID = "alertId";
    public static final String ALERT_NAME = "alertName";
    public static final String ALERT_DATE_TIME = "alertDateTime";

    //Alert Table Create
    private static final String CREATE_ALERT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALERT + " ("
            + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALERT_NAME + " TEXT, "
            + ALERT_DATE_TIME + " DATETIME)";

    public TermDbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues planToTake = new ContentValues();
        planToTake.put(STATUS_ID, statusPlanToTake.getId());
        planToTake.put(STATUS_NAME, statusPlanToTake.getName());
        ContentValues inProgress = new ContentValues();
        inProgress.put(STATUS_ID, statusInProgress.getId());
        inProgress.put(STATUS_NAME, statusInProgress.getName());
        ContentValues completed = new ContentValues();
        completed.put(STATUS_ID, statusCompleted.getId());
        completed.put(STATUS_NAME, statusCompleted.getName());
        ContentValues dropped = new ContentValues();
        dropped.put(STATUS_ID, statusDropped.getId());
        dropped.put(STATUS_NAME, statusDropped.getName());
        ContentValues performance = new ContentValues();
        performance.put(ASSESSMENT_TYPE_ID, assessTypePerformance.getId());
        performance.put(ASSESSMENT_TYPE_NAME, assessTypePerformance.getName());
        ContentValues objective = new ContentValues();
        objective.put(ASSESSMENT_TYPE_ID, assessTypeObjective.getId());
        objective.put(ASSESSMENT_TYPE_NAME, assessTypeObjective.getName());


        try {
            db.execSQL(CREATE_TERM_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
            db.execSQL(CREATE_MENTOR_TABLE);
            db.execSQL(CREATE_STATUS_TABLE);
            db.insert(TABLE_STATUS, null, planToTake);
            db.insert(TABLE_STATUS, null, inProgress);
            db.insert(TABLE_STATUS, null, completed);
            db.insert(TABLE_STATUS, null, dropped);
            //db.execSQL(INSERT_STATUS);
            db.execSQL(CREATE_ASSESSMENT_TYPE_TABLE);
            db.insert(TABLE_ASSESSMENT_TYPE, null, performance);
            db.insert(TABLE_ASSESSMENT_TYPE,null, objective);
            //db.execSQL(INSERT_ASSESSMENT_TYPE);
            db.execSQL(CREATE_ASSESSMENT_TABLE);
            db.execSQL(CREATE_NOTE_TABLE);
            db.execSQL(CREATE_ALERT_TABLE);
        } catch (SQLException e) {
            System.out.println("Error creating DB" + e);
        }
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

    //Status Table Setup
    private static final String INSERT_STATUS =
            "INSERT INTO " + TABLE_STATUS + " (" + STATUS_ID + ", " + STATUS_NAME + ") " +
                    "VALUES (0, 'Plan To Take')," +
                    "(1, 'In Progress')," +
                    "(2, 'Completed')," +
                    "(3, 'Dropped')";

    //Assessment Type Table Setup
    private static final String INSERT_ASSESSMENT_TYPE =
            "INSERT INTO " + TABLE_ASSESSMENT_TYPE + " (" + ASSESSMENT_TYPE_ID + ", " + ASSESSMENT_TYPE_NAME + ")" +
                    "VALUES (1, 'Performance')," +
                    "(2, 'Objective')";
}