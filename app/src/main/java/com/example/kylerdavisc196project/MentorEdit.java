package com.example.kylerdavisc196project;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.kylerdavisc196project.model.Mentor;

public class MentorEdit extends AppCompatActivity {
    private QueryManager QM;
    private Mentor mentorToEdit;
    private Mentor mentorToSave;
    private long mentorId;
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mentorName = findViewById(R.id.editMentorNameField);
        mentorEmail = findViewById(R.id.editMentorEmailField);
        mentorPhone = findViewById(R.id.editMentorPhoneField);
        QM = new QueryManager(MentorEdit.this);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.MENTOR_ID)) {
            mentorId = incomingIntent.getLongExtra(TermDbHandler.MENTOR_ID, 0);
            QM.open();
            mentorToEdit = QM.selectMentor(mentorId);
            QM.close();
            mentorName.setText(mentorToEdit.getName());
            mentorPhone.setText(mentorToEdit.getPhone());
            mentorEmail.setText(mentorToEdit.getEmail());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_cancel_edit_mentor) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveMentor(View view) {
        String nameString = mentorName.getText().toString();
        String phoneString = mentorPhone.getText().toString();
        String emailString = mentorEmail.getText().toString();
        if(nameString.isEmpty()) {
            Toast.makeText(this, "Please Enter a name.", Toast.LENGTH_SHORT).show();
        } else if(phoneString.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number",Toast.LENGTH_SHORT).show();
        } else if(emailString.isEmpty()) {
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
        } else {
            QM.open();
            mentorToSave = new Mentor();
            mentorToSave.setName(nameString);
            mentorToSave.setPhone(phoneString);
            mentorToSave.setEmail(emailString);
            if (mentorId == 0) {
                QM.insertMentor(mentorToSave);
                QM.close();
                finish();
            } else if (mentorId > 0) {
                mentorToSave.setId((int)(long) mentorId);
                QM.updateMentor(mentorToSave);
                QM.close();
                finish();
            }
        }
    }
}
