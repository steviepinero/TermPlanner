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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCourseAdapter();
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        Course.courseArrayList.clear(); // Clear the list before populating
        sqLiteManager.populateCourseListArray();
    }

    private void initWidgets() {
        loadFromDBToMemory();

        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Add this line

    }

    private void setCourseAdapter() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        ArrayList<Course> courses = sqLiteManager.getCoursesForTermId(termId);

        if (courses != null && !courses.isEmpty()) {
            courseAdapter = new CourseAdapter(getApplicationContext(), courses);
            courseRecyclerView.setAdapter(courseAdapter);
        } else {
            //TODO display toast message
        }
    }


    public void onCourseDetailButtonClick(View view) {
    // Get the course ID
    int courseId = getIntent().getIntExtra("courseId", -1);
    if (courseId == -1) {
        // TODO: Handle the case where the course ID is not available
    }

    // Create an intent for CourseDetailActivity
    Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
    intent.putExtra(Term.TERM_EDIT_EXTRA, termId);

    // Start CourseDetailActivity
    startActivity(intent);
}


    public void addCourse(View view) {
        Intent addCourseIntent = new Intent(this, AddCourseActivity.class);
        addCourseIntent.putExtra(Term.TERM_EDIT_EXTRA, termId);
        startActivity(addCourseIntent);
    }




}

