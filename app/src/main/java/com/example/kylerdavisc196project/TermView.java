package com.example.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylerdavisc196project.adapter.CourseAdapter;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Term;

import org.w3c.dom.Text;

import java.util.List;

public class TermView extends AppCompatActivity {
    long termId;
    private QueryManager QM;
    Term termToView;
    List<Course> coursesToView;
    ListView courseListView;
    private CourseAdapter courseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent incomingIntent = getIntent();
        courseListView = findViewById(R.id.termListView);
        QM = new QueryManager(TermView.this);
        QM.open();
        if(incomingIntent.hasExtra(TermDbHandler.TERM_ID)) {
            termId = incomingIntent.getLongExtra(TermDbHandler.TERM_ID, 0);
        }
        setUpTitleAndDates();
        setUpListView();
    }
    private void setUpTitleAndDates() {
        if(termId > 0) {
            termToView = QM.selectTerm((int) (long) termId);

            TextView termTitle = findViewById(R.id.termViewTitle);
            termTitle.setText(termToView.getName());
            TextView termStartDateText = findViewById(R.id.termViewStartDateText);
            termStartDateText.setText(termToView.getStartDate());
            TextView termEndDateText = findViewById(R.id.termViewEndDateText);
            termEndDateText.setText(termToView.getEndDate());
        }
    }
    private void setUpListView() {
        if(termId > 0) {
            coursesToView = QM.selectCoursesByTerm((int) (long) termId);
        }
        courseListView = findViewById(R.id.termViewCourseListView);
        courseAdapter = new CourseAdapter(this,coursesToView);
        courseListView.setAdapter(courseAdapter);

        //TODO: Need to find a way to do a query for status, will eventually have to tackle
        //TODO: nested queries somehow because there are nested objects.  This will be a good
        //TODO: place to figure out where I need to put the query to get the statuses.  Maybe
        //TODO: I do need to go in and put my objects back to nested objects instead of IDs,
        //TODO: either that or I need to nest the queries, and set the nested objects somehow.
        //TODO: probably need to do a join operation in the db query.  
    }

}
