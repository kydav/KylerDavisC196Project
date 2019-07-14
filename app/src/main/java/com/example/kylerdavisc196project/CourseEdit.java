package com.example.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Term;

import org.w3c.dom.Text;

public class CourseEdit extends AppCompatActivity {
    EditText courseName;
    EditText courseDescription;
    DatePicker startDate;
    DatePicker endDate;
    Spinner status;
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;
    long courseId;
    Course courseToEdit;
    Course courseToSave;
    private QueryManager QM;
    ArrayAdapter<CharSequence> adapter;
    long termId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseName = findViewById(R.id.courseEditName);
        courseDescription = findViewById(R.id.courseEditDescription);
        startDate = findViewById(R.id.courseEditStartDate);
        endDate = findViewById(R.id.courseEditEndDate);
        status = findViewById(R.id.courseEditStatusSpinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        status.setPrompt("Course Status");
        mentorName = findViewById(R.id.courseEditMentorName);
        mentorPhone = findViewById(R.id.courseEditMentorPhone);
        mentorEmail = findViewById(R.id.courseEditMentorEmail);
        QM = new QueryManager(CourseEdit.this);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
            QM.open();
            courseToEdit = QM.selectCourse(courseId);
            populate();
            QM.close();
        } else if(incomingIntent.hasExtra(TermDbHandler.TERM_ID)) {
            termId = incomingIntent.getLongExtra(TermDbHandler.TERM_ID, 0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cancel_edit_course) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void populate() {
        courseName.setText(courseToEdit.getName());
        courseDescription.setText(courseToEdit.getDescription());
        int[] startDateArray = QM.dateSplits(courseToEdit.getStartDate());
        startDate.updateDate(startDateArray[0], startDateArray[1], startDateArray[2]);
        int[] endDateArray = QM.dateSplits(courseToEdit.getEndDate());
        endDate.updateDate(endDateArray[0], endDateArray[1], endDateArray[2]);
        int spinnerPosition = adapter.getPosition(courseToEdit.getStatus());
        status.setSelection(spinnerPosition);
        mentorName.setText(courseToEdit.getMentorName());
        mentorPhone.setText(courseToEdit.getMentorPhone());
        mentorEmail.setText(courseToEdit.getMentorEmail());
    }
    public void saveCourse(View view) {
        courseToSave = new Course();
        String name = courseName.getText().toString();
        String mName = mentorName.getText().toString();
        String mEmail = mentorEmail.getText().toString();
        String mPhone = mentorPhone.getText().toString();
        String startDateTransformed = QM.dateToDB(String.valueOf(startDate.getMonth()+1)+ "/" + startDate.getDayOfMonth() + "/" + startDate.getYear());
        String endDateTransformed = QM.dateToDB(String.valueOf(endDate.getMonth()+1)+ "/" + endDate.getDayOfMonth() + "/" + endDate.getYear());
        if(name.isEmpty()) {
            Toast.makeText(this, "Enter a Course Name", Toast.LENGTH_LONG).show();
        } else if(mName.isEmpty()) {
            Toast.makeText(this, "Enter a Mentor Name", Toast.LENGTH_SHORT).show();
        } else if(mPhone.isEmpty()) {
            Toast.makeText(this, "Enter a Mentor Phone Number", Toast.LENGTH_LONG).show();
        } else if(mEmail.isEmpty()) {
            Toast.makeText(this, "Enter a Mentor Email Address", Toast.LENGTH_LONG).show();
        } else if(Integer.parseInt(endDateTransformed) <= Integer.parseInt(startDateTransformed)) {
            Toast.makeText(this,"The End Date must be after the Start Date", Toast.LENGTH_LONG).show();
        } else {
            QM.open();
            courseToSave.setName(name);
            courseToSave.setDescription(courseDescription.getText().toString());
            courseToSave.setStartDate(QM.dateToDB(String.valueOf(startDate.getMonth()+1)+ "/" + startDate.getDayOfMonth() + "/" + startDate.getYear()));
            courseToSave.setEndDate(QM.dateToDB(String.valueOf(endDate.getMonth()+1)+ "/" + endDate.getDayOfMonth() + "/" + endDate.getYear()));
            courseToSave.setStatus(status.getSelectedItem().toString());
            courseToSave.setMentorName(mName);
            courseToSave.setMentorPhone(mPhone);
            courseToSave.setMentorEmail(mEmail);

            if(courseId > 0) {
                courseToSave.setTerm(courseToEdit.getTerm());
                courseToSave.setId((int) (long)courseId);
                QM.updateCourse(courseToSave);
                QM.close();
                finish();
            } else {
                Term termToSet = QM.selectTerm((int) (long)termId);
                courseToSave.setTerm(termToSet);
                QM.insertCourse(courseToSave);
                QM.close();
                finish();
            }
        }
    }
}
