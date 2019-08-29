package com.wgu.kylerdavisc196project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wgu.kylerdavisc196project.adapter.TermAdapter;
import com.wgu.kylerdavisc196project.model.Term;

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
        getSupportActionBar().setTitle("Term List");
        QM = new QueryManager(TermList.this);
        setUpListView();
        FloatingActionButton floatingActionButton = new FloatingActionButton(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16,16,16,16);
        floatingActionButton.setLayoutParams(layoutParams);
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermEdit.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayout = findViewById(R.id.rootContainer);
        if (linearLayout != null) {
            linearLayout.addView(floatingActionButton);
        }
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
