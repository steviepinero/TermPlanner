package com.wgu.termplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AssessmentDetailActivity extends AppCompatActivity {
    private EditText dueDateEditText;
    private SQLiteManager sqLiteManager;
    private Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String debug = "onCreate in AssessmentDetailActivity";
        System.out.println(debug);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail); // layout

        dueDateEditText = findViewById(R.id.dueDateEditText);
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        checkForEditAssessment();

        /*dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(AssessmentDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dueDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });*/
        // ... rest of your code
    }

    public void saveAssessment(View view) {
        String debug = "saveAssessment in AssessmentDetailActivity";
        System.out.println(debug);


        String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        String dueDate = ((EditText) findViewById(R.id.dueDateEditText)).getText().toString();

        // Determine assessment type
        RadioGroup assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
        String type = ((RadioButton) findViewById(assessmentRadioGroup.getCheckedRadioButtonId())).getText().toString();

        if (assessment == null) { // if we're creating a new assessment
            int id = Assessment.assessmentArrayList.size();
            int courseId = getIntent().getIntExtra(Assessment.ASSESSMENT_EDIT_EXTRA, -1);
            assessment = new Assessment(id, title, dueDate, type, courseId);


            sqLiteManager.addAssessmentToDatabase(assessment);
        } else { // if we're editing an existing assessment
            assessment.setTitle(title);
            assessment.setDueDate(dueDate);
            assessment.setAssessmentType(type);

            sqLiteManager.updateAssessmentInDatabase(assessment);
        }

        // Go back to the previous activity
        finish();
    }

    public void deleteAssessment(View view) {
        if (assessment != null) {
            sqLiteManager.deleteAssessmentInDatabase(assessment.getId());
        }

        // Go back to the previous activity
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

        if (type.equals("Performance Assessment")) {
            assessmentRadioGroup.check(R.id.performanceAssessment);
        } else {
            assessmentRadioGroup.check(R.id.objectiveAssessment);
        }
    }

    public void showDatePickerDialog(View view) {
    }
}

