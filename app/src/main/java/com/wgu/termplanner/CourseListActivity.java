package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private ListView courseListView;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1);

        initWidgets();
        setCourseAdapter();
        setOnClickListener();
    }

    private void initWidgets() {
        courseListView = findViewById(R.id.courseListView);
    }

    private void setCourseAdapter() {
        List<Course> courses = (List<Course>) Course.getCourseForID(termId);
        CourseAdapter courseAdapter = new CourseAdapter(getApplicationContext(), courses);
        courseListView.setAdapter(courseAdapter);
    }

    private void setOnClickListener() {
        courseListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Course selectedCourse = (Course) courseListView.getItemAtPosition(position);
            Intent courseDetailIntent = new Intent(getApplicationContext(), CourseDetailActivity.class);
            courseDetailIntent.putExtra(Course.COURSE_EDIT_EXTRA, selectedCourse.getId());
            startActivity(courseDetailIntent);
        });
    }

    public void addCourse(View view) {
        Intent addCourseIntent = new Intent(this, AddCourseActivity.class);
        addCourseIntent.putExtra(Term.TERM_EDIT_EXTRA, termId);
        startActivity(addCourseIntent);
    }
}
