package com.wgu.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.wgu.kylerdavisc196project.adapter.AssessAdapter;
import com.wgu.kylerdavisc196project.model.Assessment;

import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private long courseId;
    private QueryManager QM;
    private List<Assessment> assessments;
    ListView assessmentListView;
    AssessAdapter assessAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Course Assessment List");
        QM = new QueryManager(AssessmentList.this);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
            setUpListView();
        }
    }
    public void onResume() {
        super.onResume();
        setUpListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_assessment) {
            Intent intent = new Intent(AssessmentList.this, AssessmentEdit.class);
            intent.putExtra(TermDbHandler.COURSE_ID, courseId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setUpListView() {
        QM.open();
        assessments = QM.selectAssessmentsByCourse(courseId);
        QM.close();
        assessmentListView = findViewById(R.id.assessmentListVIew);
        assessAdapter = new AssessAdapter(this, assessments);
        assessmentListView.setAdapter(assessAdapter);
    }

}
