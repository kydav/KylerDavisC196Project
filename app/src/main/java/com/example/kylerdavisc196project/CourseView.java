package com.example.kylerdavisc196project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylerdavisc196project.adapter.AssessAdapter;
import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.Course;
import com.example.kylerdavisc196project.model.Mentor;
import com.example.kylerdavisc196project.model.Note;

import java.util.List;

public class CourseView extends AppCompatActivity {
    long courseId;
    private QueryManager QM;
    Course courseToView;
    List<Assessment> assessToView;
    ListView assessListView;
    AssessAdapter assessAdapter;
    Button mentorButton;
    Button noteButton;
    Button assessmentButton;
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
        setUpTitleAndDates();

        noteButton = findViewById(R.id.courseViewNoteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                noteButtonClick();
            }
        });
        assessmentButton = findViewById(R.id.courseViewAssessmentButton);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                assessmentButtonClick();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_view, menu);
        return true;
    }
    public void onResume() {
        super.onResume();
        setUpTitleAndDates();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_course) {
            Intent intent = new Intent(getApplicationContext(), CourseEdit.class);
            intent.putExtra(TermDbHandler.COURSE_ID, courseId);
            startActivity(intent);
        } else if (id == R.id.action_delete_course) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this course?  This will delete all assessments and notes attached to the course.");
            builder.setCancelable(true);
            builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        QM.open();
                        QM.deleteCourse((int) (long)courseId);
                        QM.close();
                        dialog.cancel();
                        finish();
                    }
                });
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    } else if (id == R.id.action_add_alert) {
        Intent intent = new Intent(getApplicationContext(), CourseEdit.class);
        intent.putExtra(TermDbHandler.COURSE_ID, courseId);
        startActivity(intent);
    }
    return super.onOptionsItemSelected(item);
    }
    private void setUpTitleAndDates() {
        if(courseId > 0) {
            QM.open();
            courseToView = QM.selectCourse((int) (long) courseId);
            QM.close();
            TextView courseTitle = findViewById(R.id.courseViewTitle);
            courseTitle.setText(courseToView.getName());
            TextView courseStartDateText = findViewById(R.id.courseViewStartDateText);
            courseStartDateText.setText(courseToView.getStartDate());
            TextView courseEndDateText = findViewById(R.id.courseViewEndDateText);
            courseEndDateText.setText(courseToView.getEndDate());
            TextView courseDescription = findViewById(R.id.courseViewDescription);
            courseDescription.setText(courseToView.getDescription());
            TextView courseStatus = findViewById(R.id.courseViewStatusText);
            courseStatus.setText(courseToView.getStatus());
            TextView mentorName = findViewById(R.id.courseViewMentorNameField);
            mentorName.setText(courseToView.getMentorName());
            TextView mentorPhone = findViewById(R.id.courseViewMentorPhoneField);
            mentorPhone.setText(courseToView.getMentorPhone());
            TextView mentorEmail = findViewById(R.id.courseViewMentorEmailField);
            mentorEmail.setText(courseToView.getMentorEmail());
        }
    }

    private void noteButtonClick() {
        QM.open();
        Note note = QM.selectNote(courseId);
        QM.close();
        if(note.getNoteContents() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to create a note for this course?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(CourseView.this, NoteEdit.class);
                            intent.putExtra(TermDbHandler.COURSE_ID, courseId);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder.create();
            alert11.show();
        }else {
            Intent intent = new Intent(CourseView.this, NoteView.class);
            intent.putExtra(TermDbHandler.COURSE_ID, courseId);
            startActivity(intent);
        }
    }
    private void assessmentButtonClick() {

    }
}
