package com.wgu.termplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

public class CourseDetailActivity extends Activity {
    private EditText titleEditText, startDateEditText, endDateEditText, instructorEditText;
    private Button deleteButton;
    private RadioGroup statusEditText;
    private RadioButton inProgress, completed, dropped, planToTake;

    private Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initWidgets();
        checkForEditCourse();
    }


    private void initWidgets() {

        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        instructorEditText = findViewById(R.id.instructorEditText);
        statusEditText = findViewById(R.id.statusEditText);
        deleteButton = findViewById(R.id.deleteCourseButton);

    }

    private void checkForEditCourse()
    {

        Intent previousIntent = getIntent();
        int passedCourseID = previousIntent.getIntExtra(Course.COURSE_EDIT_EXTRA, -1);
        selectedCourse = Course.getCourseForID(passedCourseID);

        if (selectedCourse != null)
        {
            titleEditText.setText(selectedCourse.getTitle());
            startDateEditText.setText(selectedCourse.getStartDate());
            endDateEditText.setText(selectedCourse.getEndDate());
            instructorEditText.setText(selectedCourse.getInstructor());
            //radio button instead of text editor
            statusEditText.setTag(selectedCourse.getStatus());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }

    }

    public void saveCourse(View view) {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String startDate = String.valueOf(startDateEditText.getText());
        String endDate = String.valueOf(endDateEditText.getText());
        String instructor = String.valueOf(instructorEditText.getText());
        String status = String.valueOf(statusEditText.getCheckedRadioButtonId());

        if(selectedCourse == null) {
            int id = Course.courseArrayList.size();
            Course newCourse = new Course(id, title, startDate, endDate, instructor, status);
            Course.courseArrayList.add(newCourse);
            sqLiteManager.addCourseToDatabase(newCourse);
        }
        else
        {
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

    public void onCourseDetailButtonClick(View view) {
    }
}
