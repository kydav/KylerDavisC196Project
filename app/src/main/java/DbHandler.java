import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "termTrackerDb";

    //Term Table
    private static final String TABLE_TERM = "term";
    private static final String TERM_ID = "termId";
    private static final String TERM_NAME = "termName";
    private static final String TERM_START_DATE = "termStartDate";
    private static final String TERM_END_DATE = "termEndDate";

    private static final String CREATE_TERM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TERM + " ("
                    + TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TERM_NAME + " TEXT, "
                    + TERM_START_DATE + " DATE, "
                    + TERM_END_DATE + " DATE" + ")";

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

    private  static final String CREATE_STATUS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + " ("
            + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATUS_NAME + " TEXT)";

    //Assessment Table
    private static final String TABLE_ASSESSMENT = "assessment";
    private static final String ASSESSMENT_ID = "assessmentId";
    private static final String ASSESSMENT_NAME = "assessmentName";
    private static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    private static final String ASSESSMENT_ASSESSMENT_TYPE_ID = "assessmentAssessmentTypeId";
    private static final String ASSESSMENT_COURSE_ID = "assessmentCourseId";

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

    private static final String CREATE_ASSESSMENT_TYPE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT_TYPE + " ("
            + ASSESSMENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSESSMENT_TYPE_NAME + " TEXT)";

    //Note Table
    private static final String TABLE_NOTE = "note";
    private static final String NOTE_ID = "noteId";
    private static final String NOTE_CONTENTS = "noteContents";
    private static final String NOTE_COURSE_ID = "noteCourseId";

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

    private static final String CREATE_ALERT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALERT + " ("
            + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALERT_NAME + " TEXT, "
            + ALERT_DATE_TIME + " DATETIME)";

    private static final String INSERT_STATUS =
            "INSERT INTO " + TABLE_STATUS + " (" + STATUS_NAME + ") " +
                    "VALUES ('Plan To Take')," +
                    "('In Progress')," +
                    "('Completed')," +
                    "('Dropped')";
    private static final String INSERT_ASSESSMENT_TYPE =
            "INSERT INTO " + TABLE_ASSESSMENT_TYPE + " (" + ASSESSMENT_TYPE_NAME + ")" +
                    "VALUES ('Performance')," +
                    "('Objective')";



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

    }
}
