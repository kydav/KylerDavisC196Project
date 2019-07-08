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

import com.example.kylerdavisc196project.R;
import com.example.kylerdavisc196project.TermDbHandler;
import com.example.kylerdavisc196project.TermView;
import com.example.kylerdavisc196project.model.Term;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends ArrayAdapter<Term> {
    private Context mContext;
    private List<Term> termList = new ArrayList<>();

    public TermAdapter(@NonNull Context context, List<Term> list) {
        super(context, 0 , list);
        mContext = context;
        termList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem  == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_term, parent,false);

        Term currentTerm = termList.get(position);
        final long termId = currentTerm.getId();
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermView.class);
                intent.putExtra(TermDbHandler.TERM_ID, termId);
                mContext.startActivity(intent);
            }
        });
        TextView name = (TextView) listItem.findViewById(R.id.term_name);
        name.setText(currentTerm.getName());

        TextView start = (TextView) listItem.findViewById(R.id.term_start);
        start.setText(currentTerm.getStartDate());

        TextView end = (TextView) listItem.findViewById(R.id.term_end);
        end.setText(currentTerm.getEndDate());

        return listItem;
    }
}
