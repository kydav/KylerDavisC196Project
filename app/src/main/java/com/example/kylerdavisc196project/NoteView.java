package com.example.kylerdavisc196project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kylerdavisc196project.model.Note;

public class NoteView extends AppCompatActivity {
    private QueryManager QM;
    private long courseId;
    Note note;
    private long noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
        }
        QM = new QueryManager(NoteView.this);
        QM.open();
        note = QM.selectNote(courseId);
        noteId = note.getId();
        String courseName = QM.selectCourse((int)(long)courseId).getName() + " Note";
        TextView noteViewTitle = findViewById(R.id.noteViewCourseName);
        noteViewTitle.setText(courseName);
        TextView noteView = findViewById(R.id.noteViewNoteText);
        noteView.setText(note.getNoteContents());
        QM.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_note) {
            Intent intent = new Intent(getApplicationContext(), NoteEdit.class);
            intent.putExtra(TermDbHandler.COURSE_ID, courseId);
            startActivity(intent);
        } else if (id == R.id.action_delete_note) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this note?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            QM.open();
                            QM.deleteNote(note.getId());
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
        }
    return super.onOptionsItemSelected(item);
    }
}

