package com.wgu.termplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, 0, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Course course = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_cell, parent, false);

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView startDate = convertView.findViewById(R.id.cellStartDate);
        TextView endDate = convertView.findViewById(R.id.cellEndDate);
        TextView instructor = convertView.findViewById(R.id.cellInstructor);
        Text status = convertView.findViewById(R.id.cellStatus);

        title.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText((course.getEndDate()));
        instructor.setText((course.getInstructor()));
        status.setTextContent(course.getStatus());

        return convertView;
    }

}
