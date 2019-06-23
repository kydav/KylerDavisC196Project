package com.example.kylerdavisc196project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Term;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
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
            + "FOREIGN KEY ("+ASSESSMENT_TYPE_ID+") REFERENCES " + TABLE_ASSESSMENT_TYPE + "("+ASSESSMENT_TYPE_ID+") ON DELETE CASCADE,"
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

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TERM_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
            db.execSQL(CREATE_MENTOR_TABLE);
            db.execSQL(CREATE_STATUS_TABLE);
            db.execSQL(CREATE_ASSESSMENT_TABLE);
            db.execSQL(CREATE_ASSESSMENT_TYPE_TABLE);
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

    public void createTestData(SQLiteDatabase db) {
        try {
            db.execSQL(GENERATE_TERM_TEST_DATA);
            db.execSQL(GENERATE_MENTOR_TEST_DATA);
            db.execSQL(GENERATE_COURSE_TEST_DATA);
            db.execSQL(GENERATE_ASSESSMENT_TEST_DATA);
        } catch (SQLException e) {
            System.out.println("Error creating test data" + e);
        }
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

    //Select Courses by Term
    //TODO: Need to make Join on Mentor table and Term Table to get all data in same select so sets can use all of it.
//    public List<Course> selectCoursesByTerm(int termId) {
//        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + COURSE_ID + " = " + termId;
//        List<Course> terms = new ArrayList<>();
//
//        Log.e(LOG, selectQuery);
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//        if (c.moveToFirst()) {
//            do {
//                Course td = new Course();
//                td.setId(c.getInt((c.getColumnIndex(COURSE_ID))));
//                td.setName(c.getString((c.getColumnIndex(COURSE_NAME))));
//                td.setDescription(c.getString((c.getColumnIndex(COURSE_DESCRIPTION))));
//                td.setStartDate(c.getString((c.getColumnIndex(TERM_START_DATE))));
//                td.setEndDate(c.getString((c.getColumnIndex(TERM_END_DATE))));
//                td.setMentor
//
//                terms.add(td);
//            } while (c.moveToNext());
//        }
//        return terms;
//    }

    //Status Table Setup
    private static final String INSERT_STATUS =
            "INSERT INTO " + TABLE_STATUS + " (" + STATUS_NAME + ") " +
                    "VALUES ('Plan To Take')," +
                    "('In Progress')," +
                    "('Completed')," +
                    "('Dropped')";

    //Assessment Type Table Setup
    private static final String INSERT_ASSESSMENT_TYPE =
            "INSERT INTO " + TABLE_ASSESSMENT_TYPE + " (" + ASSESSMENT_TYPE_NAME + ")" +
                    "VALUES ('Performance')," +
                    "('Objective')";

    private static final String GENERATE_ASSESSMENT_TEST_DATA =
            "INSERT INTO " + TABLE_ASSESSMENT + " (" + ASSESSMENT_NAME + ", " + ASSESSMENT_DUE_DATE +
                    ", " + ASSESSMENT_TYPE_ID + ", " + ASSESSMENT_COURSE_ID + ")\n" +
                    "VALUES ('Course 1 Assessment', '2019-09-30', 1, 1),\n" +
                    "('Course 2 Assessment', '2019-11-30', 1, 2),\n" +
                    "('Course 3 Assessment', '2020-01-31', 2, 3),\n" +
                    "('Course 4 Assessment', '2020-03-31', 1, 4),\n" +
                    "('Course 5 Assessment', '2020-05-31', 2, 5),\n" +
                    "('Course 6 Assessment', '2020-07-31', 2, 6),\n" +
                    "('Course 7 Assessment', '2020-09-30', 1, 7),\n" +
                    "('Course 8 Assessment', '2020-11-30', 2, 8),\n" +
                    "('Course 9 Assessment', '2020-01-31', 1, 9)";

    private static final String GENERATE_COURSE_TEST_DATA =
            "INSERT INTO " + TABLE_COURSE + " (" + COURSE_NAME + ", " + COURSE_DESCRIPTION + ", " + COURSE_START_DATE +
                    ", " + COURSE_END_DATE + ", " + COURSE_STATUS_ID + ", " + COURSE_TERM_ID + ", " + COURSE_MENTOR_ID + ")\n" +
                    "VALUES ('Course 1', 'Course 1 Description', '2019-08-01', '2019-09-30', 2, 1, 1),\n" +
                    "('Course 2', 'Course 2 Description', '2019-10-01', '2019-11-30', 1, 1, 2),\n" +
                    "('Course 3', 'Course 3 Description', '2019-12-01', '2020-01-31', 1, 1, 3),\n" +
                    "('Course 4', 'Course 4 Description', '2020-02-01', '2020-03-31', 1, 2, 4),\n" +
                    "('Course 5', 'Course 5 Description', '2020-04-01', '2020-05-31', 1, 2, 5),\n" +
                    "('Course 6', 'Course 6 Description', '2020-06-01', '2020-07-31', 1, 2, 6),\n" +
                    "('Course 7', 'Course 7 Description', '2020-08-01', '2020-09-30', 1, 3, 7),\n" +
                    "('Course 8', 'Course 8 Description', '2020-10-01', '2020-11-30', 1, 3, 8),\n" +
                    "('Course 9', 'Course 9 Description', '2020-12-01', '2020-01-31', 1, 3, 9)";

    private static final String GENERATE_MENTOR_TEST_DATA =
            "INSERT INTO " + TABLE_MENTOR + " (" + MENTOR_NAME + ", " + MENTOR_PHONE + ", " + MENTOR_EMAIL + ")\n" +
                    "VALUES ('James Smith', '123-456-1234', 'james.smith@wgu.edu'),\n" +
                    "('Michael Smith', '123-456-1235', 'michael.smith@wgu.edu'),\n" +
                    "('Robert Smith', '123-456-1236', 'robert.smith@wgu.edu'),\n" +
                    "('Maria Garcia', '123-456-1237', 'maria.garcia@wgu.edu'),\n" +
                    "('David Smith', '123-456-1238', 'david.smith@wgu.edu'),\n" +
                    "('Maria Rodriguez', '123-456-1239', 'maria.rodriquez@wgu.edu'),\n" +
                    "('Mary Smith', '123-456-1241', 'mary.smith@wgu.edu'),\n" +
                    "('Maria Hernandez', '123-456-1242', 'maria.hernandez@wgu.edu'),\n" +
                    "('James Johnson', '123-456-1243', 'james.johnson@wgu.edu')";

    private static final String GENERATE_TERM_TEST_DATA =
            "INSERT INTO " + TABLE_TERM + " (" + TERM_NAME + ", " + TERM_START_DATE + ", " + TERM_END_DATE + ")\n" +
                    "VALUES ('Term 1', '2019-08-01', '2020-01-31'),\n" +
                    "('Term 2', '2020-02-01', '2020-07-31'),\n" +
                    "('Term 3', '2020-08-01', '2021-01-31')";
}
