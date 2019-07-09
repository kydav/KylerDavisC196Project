package com.example.kylerdavisc196project;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.kylerdavisc196project.adapter.CourseAdapter;
import com.example.kylerdavisc196project.adapter.TermAdapter;
import com.example.kylerdavisc196project.model.Term;

import java.util.List;

public class TermList extends AppCompatActivity {
    ListView termListView;
    TermAdapter termAdapter;
    QueryManager QM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        QM = new QueryManager(TermList.this);
        setUpListView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermEdit.class);
                startActivity(intent);
            }
        });
    }
    public void onResume() {
        super.onResume();
        setUpListView();
    }
    private void setUpListView() {
        QM.open();
        List<Term> termsToView = QM.selectAllTerms();
        termListView = findViewById(R.id.termListView);
        termAdapter = new TermAdapter(this, termsToView);
        termListView.setAdapter(termAdapter);
        QM.close();
    }

}
