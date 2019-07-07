package com.example.kylerdavisc196project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylerdavisc196project.adapter.AssessAdapter;
import com.example.kylerdavisc196project.adapter.CourseAdapter;
import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.Course;

import java.util.List;

public class CourseView extends AppCompatActivity {
    long courseId;
    private QueryManager QM;
    Course courseToView;
    List<Assessment> assessToView;
    ListView assessListView;
    AssessAdapter assessAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
        }
        QM = new QueryManager(CourseView.this);
        QM.open();
    }
    private void setUpTitleAndDates() {
        if(courseId > 0) {
            courseToView = QM.selectCourse((int) (long) courseId);

            TextView termTitle = findViewById(R.id.termViewTitle);
            termTitle.setText(courseToView.getName());
            TextView termStartDateText = findViewById(R.id.termViewStartDateText);
            termStartDateText.setText(courseToView.getStartDate());
            TextView termEndDateText = findViewById(R.id.termViewEndDateText);
            termEndDateText.setText(courseToView.getEndDate());
        }
    }
    private void setUpListView() {
        if (courseId > 0) {
            assessToView = QM.selectAssessmentsByCourse((int) (long) courseId);
        }
        //TODO:NEED TO CREATE CONTENT XML FOR COURSE VIEW AND ADD THE LISTVIEW AND ELEMENTS
        assessListView = findViewById(R.id.courseViewAssessListView);
        assessAdapter = new AssessAdapter(this, assessToView);
        assessListView.setAdapter(assessAdapter);
    }
}
