package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private int termId;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1);

        initWidgets();
        setCourseAdapter();
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCourseAdapter();
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateCourseListArray();
    }

    private void initWidgets() {
        loadFromDBToMemory();

        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Add this line

    }

    private void setCourseAdapter() {
        ArrayList<Course> courses = Course.getCoursesForTermId(termId);

        if (courses != null && !courses.isEmpty()) {
            courseAdapter = new CourseAdapter(this, courses); // Here's the change
            courseRecyclerView.setAdapter(courseAdapter);
        } else {
            //TODO display toast message
        }
    }


    private void setOnClickListener() {
    /*    courseRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), (view, position) -> {
                    Course selectedCourse = courseAdapter.getItem(position);
                    Intent courseDetailIntent = new Intent(getApplicationContext(), CourseDetailActivity.class);
                    courseDetailIntent.putExtra(Course.COURSE_EDIT_EXTRA, selectedCourse.getId());
                    startActivity(courseDetailIntent);
                }));*/
    }

    public void addCourse(View view) {
        Intent addCourseIntent = new Intent(this, AddCourseActivity.class);
        addCourseIntent.putExtra(Term.TERM_EDIT_EXTRA, termId);
        startActivity(addCourseIntent);
    }
}

