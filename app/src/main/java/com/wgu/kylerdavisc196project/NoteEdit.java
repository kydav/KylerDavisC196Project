package com.wgu.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.kylerdavisc196project.model.Note;

public class NoteEdit extends AppCompatActivity {
    private long courseId;
    private QueryManager QM;
    Note note;
    EditText noteView;
    Boolean priorNoteExists = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Note");
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
        }
        QM = new QueryManager(NoteEdit.this);
        QM.open();
        note = QM.selectNote(courseId);
        TextView noteViewTitle = findViewById(R.id.noteEditCourseName);
        noteView = findViewById(R.id.noteEditNoteText);
        String courseName = QM.selectCourse((int)(long)courseId).getName() + " Note";
        noteViewTitle.setText(courseName);
        if(note.getNoteContents() != null) {
            noteView.setText(note.getNoteContents());
            priorNoteExists = true;
        }
        QM.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_cancel_edit_note) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveNote(View view) {
        String noteToSave = noteView.getText().toString();
        if(noteToSave.isEmpty()) {
            Toast.makeText(this, "Please enter text into the note field.", Toast.LENGTH_SHORT).show();
        }else {
            QM.open();
            if(priorNoteExists) {
                QM.updateNote(noteToSave, note.getId());
            } else {
                QM.insertNote(noteToSave, courseId);
            }
            QM.close();
            finish();
        }

    }
}
