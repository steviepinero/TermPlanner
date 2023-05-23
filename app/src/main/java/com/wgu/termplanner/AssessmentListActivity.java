package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssessmentListActivity extends AppCompatActivity {

    private RecyclerView assessmentRecyclerView;
    private int courseId;
    private AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        courseId = getIntent().getIntExtra(Course.COURSE_EDIT_EXTRA, -1);

        initWidgets();
        setAssessmentAdapter();
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAssessmentAdapter();
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateAssessmentListArray();
    }

    private void initWidgets() {
        loadFromDBToMemory();


        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAssessmentAdapter() {
        ArrayList<Assessment> assessments = Assessment.getAssessmentsForCourseId(courseId);

        if (assessments != null && !assessments.isEmpty()) {
            assessmentAdapter = new AssessmentAdapter(getApplicationContext(), assessments);
            assessmentRecyclerView.setAdapter(assessmentAdapter);
        } else {
            //TODO display toast message
        }
    }


    private void setOnClickListener() {
        assessmentRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), (view, position) -> {
                    Assessment selectedAssessment = assessmentAdapter.getItem(position);
                    Intent assessmentDetailIntent = new Intent(getApplicationContext(), AssessmentDetailActivity.class);
                    assessmentDetailIntent.putExtra(Assessment.ASSESSMENT_EDIT_EXTRA, selectedAssessment.getId());
                    startActivity(assessmentDetailIntent);
                }));
    }

    public void addAssessment(View view) {
        Intent addAssessmentIntent = new Intent(this, AddAssessmentActivity.class);
        addAssessmentIntent.putExtra(Course.COURSE_EDIT_EXTRA, courseId);
        startActivity(addAssessmentIntent);
    }
}
