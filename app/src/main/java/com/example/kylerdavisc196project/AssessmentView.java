package com.example.kylerdavisc196project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kylerdavisc196project.model.Assessment;

public class AssessmentView extends AppCompatActivity {
    QueryManager QM;
    long assessmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            assessmentId = incomingIntent.getLongExtra(TermDbHandler.ASSESSMENT_ID, 0);
        }
        QM = new QueryManager(AssessmentView.this);
        QM.open();
    }
}
