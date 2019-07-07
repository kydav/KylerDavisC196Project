package com.example.kylerdavisc196project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kylerdavisc196project.AssessmentView;
import com.example.kylerdavisc196project.R;
import com.example.kylerdavisc196project.TermDbHandler;
import com.example.kylerdavisc196project.TermView;
import com.example.kylerdavisc196project.model.Assessment;
import com.example.kylerdavisc196project.model.Term;

import java.util.ArrayList;
import java.util.List;

public class AssessAdapter extends ArrayAdapter<Assessment> {
    private Context mContext;
    private List<Assessment> assessList = new ArrayList<>();

    public AssessAdapter(@NonNull Context context, List<Assessment> list) {
        super(context, 0 , list);
        mContext = context;
        assessList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem  == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_assessment, parent,false);

        Assessment currentAssessment = assessList.get(position);
        final long assessmentId = currentAssessment.getId();
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AssessmentView.class);
                intent.putExtra(TermDbHandler.ASSESSMENT_ID, assessmentId);
                mContext.startActivity(intent);
            }
        });
        TextView name = (TextView) listItem.findViewById(R.id.assessment_name);
        name.setText(currentAssessment.getName());

        TextView start = (TextView) listItem.findViewById(R.id.assessment_type);
        start.setText(currentAssessment.getType().getName());

        TextView end = (TextView) listItem.findViewById(R.id.assessment_due);
        end.setText(currentAssessment.getDueDate());

        return listItem;
    }
}
