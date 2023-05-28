package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Assessment.assessmentArrayList.clear(); // Clear the list before populating
        sqLiteManager.populateAssessmentListArray();
    }

    private void initWidgets() {
        loadFromDBToMemory();


        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAssessmentAdapter() {

        System.out.println(courseId + " Course ID value in SetAssessmentAdapter");

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        ArrayList<Assessment> assessments = sqLiteManager.getAssessmentsForCourseId(courseId);

        if (assessments == null ) {
            Toast.makeText(this, "Assessment is null or empty", Toast.LENGTH_SHORT).show();

        } else {
            assessmentAdapter = new AssessmentAdapter(getApplicationContext(), assessments);
            assessmentRecyclerView.setAdapter(assessmentAdapter);
        }
    }


    private void setOnClickListener() {
        assessmentRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), (view, position) -> {
                    Assessment selectedAssessment = assessmentAdapter.getItem(position);

                    //create an intent for AssessmentDetailActivity
                    Intent assessmentDetailIntent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
                    assessmentDetailIntent.putExtra(Assessment.ASSESSMENT_EDIT_EXTRA, selectedAssessment.getId());

                    startActivity(assessmentDetailIntent);
                }));
    }

    public void onAssessmentDetailButtonClick(View view) {
        int assessmentId = getIntent().getIntExtra(Assessment.ASSESSMENT_EDIT_EXTRA, -1);
        if (assessmentId == -1) {
            Toast.makeText(this,"Assessment list is empty", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
        intent.putExtra(Course.COURSE_EDIT_EXTRA, courseId);
        startActivity(intent);
    }

    public void addAssessment(View view) {
        Intent addAssessmentIntent = new Intent(this, AddAssessmentActivity.class);
        addAssessmentIntent.putExtra(Course.COURSE_EDIT_EXTRA, courseId);
        startActivity(addAssessmentIntent);
    }
}
