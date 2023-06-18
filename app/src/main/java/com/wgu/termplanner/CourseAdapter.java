package com.wgu.termplanner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    //TODO FIX BUG WHERE EDIT COURSE VIEW FAILS UNTIL YOU NAVIGATE AWAY FROM COURSES, ie Edit term > back button > view courses > edit course
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
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
        holder.phone.setText(course.getInstructorPhone());
        holder.email.setText((course.getInstructorEmail()));
        holder.status.setText(course.getStatus());
        holder.note.setText(course.getNote());


        holder.viewAssessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssessmentListActivity.class);
                intent.putExtra(Course.COURSE_EDIT_EXTRA, course.getId());  // Pass the course ID to the AssessmentListActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }



    public class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView title, startDate, endDate, instructor, phone, email, status, note;
        Button viewAssessmentsButton, editCourseButton, shareNoteButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cellTitle);
            startDate = itemView.findViewById(R.id.cellStartDate);
            endDate = itemView.findViewById(R.id.cellEndDate);
            instructor = itemView.findViewById(R.id.cellInstructor);
            phone = itemView.findViewById(R.id.cellInstructorPhone);
            email = itemView.findViewById(R.id.cellInstructorEmail);
            status = itemView.findViewById(R.id.cellStatus);
            note = itemView.findViewById(R.id.cellNote);
            viewAssessmentsButton = itemView.findViewById(R.id.viewAssessmentsButton);

            editCourseButton = itemView.findViewById(R.id.editCourseButton);
            editCourseButton.setOnClickListener(v -> {
                System.out.println("editCourseButton in CourseAdapter");
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Get the course at this position
                    Course selectedCourse = courses.get(position);

                    // Create an intent for CourseDetailActivity
                    Intent intent = new Intent(context, CourseDetailActivity.class);
                    intent.putExtra(Course.COURSE_EDIT_EXTRA, selectedCourse.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    // Start CourseDetailActivity
                    context.startActivity(intent);
                }
            });

            shareNoteButton = itemView.findViewById(R.id.shareNoteButton);
            shareNoteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String note = courses.get(position).getNote();
                    shareViaSMS(note);
                }
            });
        }

        private void shareViaSMS(String note) {
            System.out.println("shareViaSMS in CourseAdapter ViewHolder");

            Uri sendSMS = Uri.parse("smsto:");
            Intent smsIntent = new Intent(Intent.ACTION_VIEW, sendSMS);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



            smsIntent.putExtra("sms_body", note);
            System.out.println("Sharing note - " + note);

            // Verify it resolves
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(smsIntent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                context.startActivity(smsIntent);
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Toast.makeText(context, "Sharing note - " + note, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No SMS app found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public Course getItem(int position) {
        return courses.get(position);
    }


}


