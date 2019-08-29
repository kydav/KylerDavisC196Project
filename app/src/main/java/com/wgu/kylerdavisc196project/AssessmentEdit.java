package com.wgu.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgu.kylerdavisc196project.model.Assessment;

public class AssessmentEdit extends AppCompatActivity {
    EditText assessName;
    DatePicker assessDueDate;
    Spinner assessType;
    long courseId;
    long assessmentId;
    Assessment assessment;
    Assessment assessmentToSave;
    QueryManager QM;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessName = findViewById(R.id.editAssName);
        assessDueDate = findViewById(R.id.editAssDatePicker);
        assessType = findViewById(R.id.editAssTypeSpinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessType.setAdapter(adapter);
        assessType.setPrompt("Assessment Type");
        QM = new QueryManager(AssessmentEdit.this);
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.COURSE_ID)) {
            courseId = incomingIntent.getLongExtra(TermDbHandler.COURSE_ID, 0);
            getSupportActionBar().setTitle("Add Assessment");
        } else if(incomingIntent.hasExtra(TermDbHandler.ASSESSMENT_ID)) {
            assessmentId = incomingIntent.getLongExtra(TermDbHandler.ASSESSMENT_ID, 0);
            getSupportActionBar().setTitle("Edit Assessment");
            populate();
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
    public void onResume() {
        super.onResume();
        if(assessmentId > 0) {
            populate();
        }
    }
    public void populate() {
        QM.open();
        assessment = QM.selectAssessment(assessmentId);
        assessName.setText(assessment.getName());
        int[] dueDateArray = QM.dateSplits(assessment.getDueDate());
        assessDueDate.updateDate(dueDateArray[0], dueDateArray[1], dueDateArray[2]);
        int spinnerPosition = adapter.getPosition(assessment.getType());
        assessType.setSelection(spinnerPosition);
        QM.close();
    }
    public void saveAssessment(View view) {
        assessmentToSave = new Assessment();
        String name = assessName.getText().toString();
        String dueDate = QM.dateToDB(String.valueOf(assessDueDate.getMonth()+1)+ "/" + assessDueDate.getDayOfMonth() + "/" + assessDueDate.getYear());
        if(name.isEmpty()) {
            Toast.makeText(this, "Enter an Assessment Name", Toast.LENGTH_LONG).show();
        } else {
            assessmentToSave.setName(name);
            assessmentToSave.setDueDate(dueDate);
            assessmentToSave.setType(assessType.getSelectedItem().toString());
            assessmentToSave.setCourseId((int) (long)courseId);
            QM.open();
            if (assessmentId > 0) {
                assessmentToSave.setId((int) (long)assessmentId);
                QM.updateAssessment(assessmentToSave);
                QM.close();
                finish();
            } else {
                QM.insertAssessment(assessmentToSave);
                QM.close();
                finish();
            }
        }
    }

}
