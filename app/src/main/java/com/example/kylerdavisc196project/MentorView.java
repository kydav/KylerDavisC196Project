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
import android.widget.TextView;

import com.example.kylerdavisc196project.model.Mentor;

public class MentorView extends AppCompatActivity {
    private QueryManager QM;
    long mentorId;
    private Mentor mentorToView;
    TextView title;
    TextView mentorPhone;
    TextView mentorEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.MENTOR_ID)) {
            mentorId = incomingIntent.getLongExtra(TermDbHandler.MENTOR_ID, 0);
        }
        QM = new QueryManager(MentorView.this);
        title = findViewById(R.id.mentorViewTitle);
        mentorPhone = findViewById(R.id.mentorViewPhoneText);
        mentorEmail = findViewById(R.id.mentorViewEmailText);
        populate();
    }
    private void populate() {
        QM.open();
        mentorToView = QM.selectMentor(mentorId);
        title.setText(mentorToView.getName());
        mentorPhone.setText(mentorToView.getPhone());
        mentorEmail.setText(mentorToView.getEmail());
        QM.close();
    }
    public void onResume() {
        super.onResume();
        populate();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_mentor) {
            Intent intent = new Intent(getApplicationContext(), MentorEdit.class);
            intent.putExtra(TermDbHandler.MENTOR_ID, mentorId);
            startActivity(intent);
        } else if (id == R.id.action_delete_mentor) {
            AlertDialog.Builder mentorViewBuilder = new AlertDialog.Builder(this);
            mentorViewBuilder.setMessage("Are you sure you want to delete this mentor?");
            mentorViewBuilder.setCancelable(true);
            mentorViewBuilder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        //TODO FIX THIS IT IS CURRENTLY BORKED AND FOR SOME REASON IS DELETING THE ENTIRE COURSE AS WELL AS THE MENTOR...
                        public void onClick(DialogInterface dialog, int id) {
                            QM.open();
                            QM.deleteMentor(mentorId);
                            QM.close();
                            dialog.cancel();
                            finish();
                        }
                    });
            mentorViewBuilder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog mentorViewAlert = mentorViewBuilder.create();
            mentorViewAlert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
