package com.example.kylerdavisc196project.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kylerdavisc196project.R;
import com.example.kylerdavisc196project.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {
    private Context mContext;
    private List<Course> courseList = new ArrayList<>();

    public CourseAdapter(@NonNull Context context, List<Course> list) {
        super(context, 0 , list);
        mContext = context;
        courseList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem  == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_course, parent,false);

            Course currentCourse = courseList.get(position);
            TextView name = (TextView) listItem.findViewById(R.id.course_name);
            name.setText(currentCourse.getName());

            TextView start = (TextView) listItem.findViewById(R.id.course_start);
            start.setText(currentCourse.getStartDate());

            TextView end = (TextView) listItem.findViewById(R.id.course_end);
            end.setText(currentCourse.getEndDate());

            TextView status = (TextView) listItem.findViewById(R.id.course_status);
            status.setText(currentCourse.getStatus().getName());

            return listItem;

    }
}
