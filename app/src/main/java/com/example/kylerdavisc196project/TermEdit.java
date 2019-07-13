package com.example.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kylerdavisc196project.model.Term;

public class TermEdit extends AppCompatActivity {
    long termId;
    private QueryManager QM;
    private Term termToSave;
    private Term termtoEdit;
    EditText termTitleEditText;
    EditText termStartDateEditText;
    EditText termEndDateEditText;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent incomingIntent = getIntent();
        QM = new QueryManager(TermEdit.this);
        QM.open();
        termTitleEditText = findViewById(R.id.termEditTitleField);
        termStartDateEditText = findViewById(R.id.termEditStartDateField);
        termEndDateEditText = findViewById(R.id.termEditEndDateField);
        if(incomingIntent.hasExtra(TermDbHandler.TERM_ID)) {
            termId = incomingIntent.getLongExtra(TermDbHandler.TERM_ID, 0);
            termtoEdit = QM.selectTerm((int) (long)termId);
            getSupportActionBar().setTitle("Edit Term");
            populate();
        }
        saveButton = findViewById(R.id.termEditSaveButton);
        termToSave = new Term();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_cancel_edit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void populate() {
        if(termId != 0) {
            termTitleEditText.setText(termtoEdit.getName());
            termStartDateEditText.setText(termtoEdit.getStartDate());
            termEndDateEditText.setText(termtoEdit.getEndDate());
        }
    }
    public void saveTerm(View view) {
        String titleString = termTitleEditText.getText().toString();
        String startDateString = termStartDateEditText.getText().toString();
        String endDateString = termEndDateEditText.getText().toString();
        if(titleString.isEmpty()) {
            Toast.makeText(this, "Please Enter a Title.", Toast.LENGTH_SHORT).show();
        } else if(startDateString.isEmpty()) {
            Toast.makeText(this, "Please enter a Start Date",Toast.LENGTH_SHORT).show();
        } else if(endDateString.isEmpty()) {
            Toast.makeText(this, "Please enter an End Date", Toast.LENGTH_SHORT).show();
        } else {
            termToSave = new Term();
            termToSave.setName(titleString);
            startDateString = QM.dateToDB(startDateString);
            termToSave.setStartDate(startDateString);
            endDateString = QM.dateToDB(endDateString);
            termToSave.setEndDate(endDateString);
            if (termId == 0) {
                QM.insertTerm(termToSave);
                QM.close();
                finish();

            } else if (termId > 0) {
                termToSave.setId((int)(long)termId);
                QM.update(termToSave);
                QM.close();
                finish();
            }
        }
    }

}
