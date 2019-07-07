package com.example.kylerdavisc196project;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kylerdavisc196project.model.Term;

public class TermEdit extends AppCompatActivity {
    long termId;
    private QueryManager QM;
    private Term termToSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        Uri uri = ActivityCompat.getReferrer(TermEdit.this);
        Intent incomingIntent = getIntent();
        QM = new QueryManager(TermEdit.this);
        QM.open();
        if(incomingIntent.hasExtra(TermDbHandler.TERM_ID)) {
            termId = incomingIntent.getLongExtra(TermDbHandler.TERM_ID, 0);
        }
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
            Intent intent = new Intent(getApplicationContext(),TermView.class);
            intent.putExtra(TermDbHandler.TERM_ID, termId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void save() {
        if(termId == 0) {
            QM.insertTerm(termToSave);
        }else if(termId > 0) {
            QM.update(termToSave);
        }
    }
}
