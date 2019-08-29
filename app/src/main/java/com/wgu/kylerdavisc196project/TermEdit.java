package com.wgu.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.kylerdavisc196project.model.Term;

public class TermEdit extends AppCompatActivity {
    long termId;
    private QueryManager QM;
    private Term termToSave;
    private Term termtoEdit;
    EditText termTitleEditText;
    DatePicker termStartDateEdit;
    DatePicker termEndDateEdit;
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
        termStartDateEdit = findViewById(R.id.termEditStartDatePicker);
        termEndDateEdit = findViewById(R.id.termEditEndDatePicker);
        if(incomingIntent.hasExtra(TermDbHandler.TERM_ID)) {
            termId = incomingIntent.getLongExtra(TermDbHandler.TERM_ID, 0);
            termtoEdit = QM.selectTerm((int) (long)termId);
            getSupportActionBar().setTitle("Edit Term");
            populate();
        } else {
            getSupportActionBar().setTitle("Add Term");
        }
        QM.close();
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
            int[] startDateArray = QM.dateSplits(termtoEdit.getStartDate());
            termStartDateEdit.updateDate(startDateArray[0], startDateArray[1], startDateArray[2]);
            int[] endDateArray= QM.dateSplits(termtoEdit.getEndDate());
            termEndDateEdit.updateDate(endDateArray[0], endDateArray[1], endDateArray[2]);
        }
    }
    public void saveTerm(View view) {
        String titleString = termTitleEditText.getText().toString();
        String startDateString = QM.dateToDB(String.valueOf(termStartDateEdit.getMonth() +1) + "/" + termStartDateEdit.getDayOfMonth() + "/" + termStartDateEdit.getYear());
        String endDateString = QM.dateToDB(String.valueOf(termEndDateEdit.getMonth() +1) + "/" + termEndDateEdit.getDayOfMonth() + "/" + termEndDateEdit.getYear());
        if(titleString.isEmpty()) {
            Toast.makeText(this, "Please Enter a Title.", Toast.LENGTH_SHORT).show();
        } else if(Integer.parseInt(endDateString) <= Integer.parseInt(startDateString)) {
            Toast.makeText(this, "The End Date must be after the Start Date",Toast.LENGTH_SHORT).show();
        }  else {
            termToSave = new Term();
            termToSave.setName(titleString);
            termToSave.setStartDate(startDateString);
            termToSave.setEndDate(endDateString);
            QM.open();
            if (termId == 0) {
                QM.insertTerm(termToSave);
                QM.close();
                finish();

            } else if (termId > 0) {
                termToSave.setId((int)(long)termId);
                QM.updateTerm(termToSave);
                QM.close();
                finish();
            }
        }
    }

}
