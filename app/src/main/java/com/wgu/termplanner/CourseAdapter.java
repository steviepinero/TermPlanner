package com.wgu.termplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cell, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.title.setText(course.getTitle());
        holder.startDate.setText(course.getStartDate());
        holder.endDate.setText(course.getEndDate());
        holder.instructor.setText(course.getInstructor());
        holder.status.setText(course.getStatus());

        holder.viewAssessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssessmentListActivity.class);
                intent.putExtra("COURSE_ID", course.getId());  // Pass the course ID to the AssessmentListActivity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView title, startDate, endDate, instructor, status;
        Button viewAssessmentsButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cellTitle);
            startDate = itemView.findViewById(R.id.cellStartDate);
            endDate = itemView.findViewById(R.id.cellEndDate);
            instructor = itemView.findViewById(R.id.cellInstructor);
            status = itemView.findViewById(R.id.cellStatus);
            viewAssessmentsButton = itemView.findViewById(R.id.viewAssessmentsButton);

        }
    }

    public Course getItem(int position) {
        return courses.get(position);
    }

}


