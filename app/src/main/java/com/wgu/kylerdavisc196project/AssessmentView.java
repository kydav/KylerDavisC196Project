package com.wgu.kylerdavisc196project;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.kylerdavisc196project.model.Assessment;

public class AssessmentView extends AppCompatActivity {
    long assessmentId;
    QueryManager QM;
    Assessment assessmentToView;
    TextView name;
    TextView dueDate;
    TextView type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assessment Detail");
        Intent incomingIntent = getIntent();
        if(incomingIntent.hasExtra(TermDbHandler.ASSESSMENT_ID)) {
            assessmentId = incomingIntent.getLongExtra(TermDbHandler.ASSESSMENT_ID, 0);
        }
        QM = new QueryManager(AssessmentView.this);
        setUpItems();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_view, menu);
        return true;
    }
    public void onResume() {
        super.onResume();
        setUpItems();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_assessment) {
            Intent intent = new Intent(getApplicationContext(), AssessmentEdit.class);
            intent.putExtra(TermDbHandler.ASSESSMENT_ID, assessmentId);
            startActivity(intent);
        } else if (id == R.id.action_delete_assessment) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this assessment?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            QM.open();
                            QM.deleteAssessment((int) (long)assessmentId);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to set an alert for the due date for this assessment?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String[] extraArray = {"assessment", String.valueOf(assessmentId)};
                            Long timeInMillis = QM.timeInMillis(assessmentToView.getDueDate());
                            Intent intent = new Intent(AssessmentView.this, MyReceiver.class);
                            intent.putExtra("frame", extraArray);
                            PendingIntent sender = PendingIntent.getBroadcast(AssessmentView.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,sender);
                            Toast.makeText(AssessmentView.this, "Alert Created", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
            builder.setNeutralButton(
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
    private void setUpItems() {
        if(assessmentId > 0) {
            QM.open();
            assessmentToView = QM.selectAssessment(assessmentId);
            QM.close();
            TextView name = findViewById(R.id.assViewName);
            TextView dueDate = findViewById(R.id.assViewDueDate);
            TextView type = findViewById(R.id.assViewType);
            name.setText(assessmentToView.getName());
            dueDate.setText(assessmentToView.getDueDate());
            type.setText(assessmentToView.getType());
        }
    }

}
