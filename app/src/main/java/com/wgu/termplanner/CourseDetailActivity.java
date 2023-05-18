package com.wgu.termplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class CourseDetailActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText, instructorEditText;
    private RadioGroup statusRadioGroup;
    private Course selectedCourse;
    private RecyclerView assessmentRecyclerView;
    private AssessmentAdapter assessmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initWidgets();
        checkForEditCourse();

        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Assessment> assessments = Assessment.getAssessmentsForCourseId(selectedCourse.getId());

        assessmentAdapter = new AssessmentAdapter(this, assessments);
        assessmentRecyclerView.setAdapter(assessmentAdapter);

    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        instructorEditText = findViewById(R.id.instructorEditText);
        statusRadioGroup = findViewById(R.id.statusRadioGroup);
    }

    private void checkForEditCourse() {
        Intent previousIntent = getIntent();
        int passedCourseID = previousIntent.getIntExtra(Course.COURSE_EDIT_EXTRA, -1);
        selectedCourse = Course.getCourseById(passedCourseID);

        if (selectedCourse != null) {
            titleEditText.setText(selectedCourse.getTitle());
            startDateEditText.setText(selectedCourse.getStartDate());
            endDateEditText.setText(selectedCourse.getEndDate());
            instructorEditText.setText(selectedCourse.getInstructor());

            String status = selectedCourse.getStatus();
            for (int i = 0; i < statusRadioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) statusRadioGroup.getChildAt(i);
                if (radioButton.getText().toString().equals(status)) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }
    }

    public void saveCourse(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String startDate = String.valueOf(startDateEditText.getText());
        String endDate = String.valueOf(endDateEditText.getText());
        String instructor = String.valueOf(instructorEditText.getText());

        int selectedStatusId = statusRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedStatusId);
        String status = selectedRadioButton.getText().toString();

        if (selectedCourse == null) {
            int id = Course.courseArrayList.size();
            int termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1);
            selectedCourse = new Course(id, title, startDate, endDate, status, instructor, termId);
            Course.courseArrayList.add(selectedCourse);
            sqLiteManager.addCourseToDatabase(selectedCourse);
        } else {
            selectedCourse.setTitle(title);
            selectedCourse.setStartDate(startDate);
            selectedCourse.setEndDate(endDate);
            selectedCourse.setInstructor(instructor);
            selectedCourse.setStatus(status);
            sqLiteManager.updateCourseInDatabase(selectedCourse);
        }

        finish();
    }

    public void deleteCourse(View view) {
        selectedCourse.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateCourseInDatabase(selectedCourse);
        finish();
    }
}
