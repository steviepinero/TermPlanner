package com.wgu.termplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddAssessmentActivity extends AppCompatActivity {

    private EditText titleEditText, dueDateEditText;
    private RadioGroup assessmentRadioGroup;
    private int courseId, day, month, year;
    private SQLiteManager sqLiteManager;
    private Assessment assessment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String debug = "onCreate in AddAssessmentActivity";
        System.out.println(debug);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        initWidgets();

        courseId = getIntent().getIntExtra(Course.COURSE_EDIT_EXTRA, -1);
        checkForEditAssessment();

    }

    private void initWidgets() {
        String debug = "initWidgets in AddAssessmentActivity";
        System.out.println(debug);
        System.out.println(this);

        titleEditText = findViewById(R.id.titleEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);

//TODO FIX THE DATE PICKER & IMPLEMENT IT FOR EACH EDIT VIEW

    }

    public void saveAssessment(View view) {
        String debug = "saveAssessment in AddAssessmentActivity";
        System.out.println(debug);
        System.out.println(this);


        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        Log.d("AddAssessmentActivity", "titleEditText: " + titleEditText + ", dueDateEditText: " + dueDateEditText);

        String title = titleEditText.getText().toString();
        String endDate = dueDateEditText.getText().toString();

        int selectedTypeId = assessmentRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedTypeId);
        String type = selectedRadioButton.getText().toString();


        if (assessment == null) { // if we're creating a new assessment
            int id = Assessment.assessmentArrayList.size();
            courseId = getIntent().getIntExtra(Course.COURSE_EDIT_EXTRA, assessment.getCourseId());
            assessment = new Assessment(id, title, endDate, type, courseId);


            sqLiteManager.addAssessmentToDatabase(assessment);
        } else { // if we're editing an existing assessment
            assessment.setTitle(title);
            assessment.setDueDate(endDate);
            assessment.setAssessmentType(type);

            sqLiteManager.updateAssessmentInDatabase(assessment);
        }
        finish();
    }

    public void checkForEditAssessment() {
        // Get assessment from intent
        Intent intent = getIntent();
        int assessmentId = intent.getIntExtra("ASSESSMENT_ID", -1);

        if (assessmentId != -1) {
            this.assessment = sqLiteManager.getAssessmentById(assessmentId);
            fillFieldsForEdit();
        }
    }

    private void fillFieldsForEdit() {
        ((EditText) findViewById(R.id.titleEditText)).setText(assessment.getTitle());
        ((EditText) findViewById(R.id.dueDateEditText)).setText(assessment.getDueDate());

        RadioGroup assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
        String type = assessment.getAssessmentType();

        if ("Performance Assessment".equals(type)) {
            assessmentRadioGroup.check(R.id.performanceAssessment);
        } else {
            assessmentRadioGroup.check(R.id.objectiveAssessment);
        }
    }
}

