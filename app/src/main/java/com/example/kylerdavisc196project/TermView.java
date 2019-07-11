package com.example.kylerdavisc196project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylerdavisc196project.adapter.CourseAdapter;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Term;

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
        getSupportActionBar().setTitle("Term Detail");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        termId = termToView.getId();
        if(id == R.id.action_edit_term) {
            Intent intent = new Intent(getApplicationContext(),TermEdit.class);

            intent.putExtra(TermDbHandler.TERM_ID, termId);
            startActivity(intent);
        }else if(id == R.id.action_delete_term) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to delete this term?  This will delete all courses and assessments attached to the term.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            QM.deleteTerm((int) (long)termId);
                            dialog.cancel();
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else if(id == R.id.action_add_course) {
            Intent intent = new Intent(TermView.this, CourseEdit.class);
            intent.putExtra(TermDbHandler.TERM_ID, termId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void onResume() {
        super.onResume();
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
        if (termId > 0) {
            coursesToView = QM.selectCoursesByTerm((int) (long) termId);
        }
        courseListView = findViewById(R.id.termViewCourseListView);
        courseAdapter = new CourseAdapter(this, coursesToView);
        courseListView.setAdapter(courseAdapter);
    }
}
