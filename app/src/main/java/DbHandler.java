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

    //Course Table
    private static final String TABLE_COURSE = "course";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_NAME = "courseName";
    private static final String COURSE_START_DATE = "courseStartDate";
    private static final String COURSE_END_DATE = "courseEndDate";
    private static final String COURSE_STATUS_ID = "courseStatus";
    private static final String COURSE_TERM_ID = "courseTermId";

    //Status Table
    private static final String TABLE_STATUS = "status";
    private static final String STATUS_ID = "statusId";
    private static final String STATUS_NAME = "statusName";

    //Assessment Table
    private static final String TABLE_ASSESSMENT = "assessment";
    private static final String ASSESSMENT_ID = "assessmentId";
    private static final String ASSESSMENT_NAME = "assessmentName";
    private static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    private static final String ASSESSMENT_ASSESSMENT_TYPE_ID = "assessmentAssessmentTypeId";
    private static final String ASSESSMENT_COURSE_ID = "assessmentCourseId";

    //Assessment Type Table
    private static final String TABLE_ASSESSMENT_TYPE = "assessmentType";
    private static final String ASSESSMENT_TYPE_ID = "assessmentTypeId";

    //Note Table
    private static final String TABLE_NOTE = "note";
    private static final String NOTE_ID = "noteId";
    private static final String NOTE_CONTENTS = "noteContents";
    private static final String NOTE_COURSE_ID = "noteCourseId";

    //Alert Table
    private static final String TABLE_ALERT = "alert";
    private static final String ALERT_ID = "alertId";
    private static final String ALERT_NAME = "alertName";
    private static final String ALERT_DATE_TIME = "alertDateTime";

    //testing

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
