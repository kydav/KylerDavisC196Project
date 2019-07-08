package com.example.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylerdavisc196project.adapter.AssessAdapter;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
        }
        QM = new QueryManager(CourseView.this);
        QM.open();
        setUpTitleAndDates();
    }
    private void setUpTitleAndDates() {
        if(courseId > 0) {
            courseToView = QM.selectCourse((int) (long) courseId);

            TextView courseTitle = findViewById(R.id.courseViewTitle);
            courseTitle.setText(courseToView.getName());
            TextView courseStartDateText = findViewById(R.id.courseViewStartDateText);
            courseStartDateText.setText(courseToView.getStartDate());
            TextView courseEndDateText = findViewById(R.id.courseViewEndDateText);
            courseEndDateText.setText(courseToView.getEndDate());
            TextView courseDescription = findViewById(R.id.courseViewDescription);
            courseDescription.setText(courseToView.getDescription());
            TextView courseStatus = findViewById(R.id.courseViewStatusText);
            courseStatus.setText(courseToView.getStatus().getName());
        }
    }
//    private void setUpListView() {
//        if (courseId > 0) {
//            assessToView = QM.selectAssessmentsByCourse((int) (long) courseId);
//        }
//
//        assessListView = findViewById(R.id.courseViewAssessListView);
//        assessAdapter = new AssessAdapter(this, assessToView);
//        assessListView.setAdapter(assessAdapter);
//    }
}
