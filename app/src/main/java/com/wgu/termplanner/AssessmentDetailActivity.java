package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.cert.CertPathChecker;

public class AssessmentDetailActivity extends AppCompatActivity {
    private EditText dueDateEditText, titleEditText;
    private SQLiteManager sqLiteManager;
    private Assessment selectedAssessment;
    private RadioGroup assessmentRadioGroup;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String debug = "onCreate in AssessmentDetailActivity";
        System.out.println(debug);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail); // layout
        initWidgets();

        checkForEditAssessment();

    }

    public void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        deleteButton = findViewById(R.id.deleteAssessmentButton);

        // Initialize the SQLiteManager
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
    }

    public void saveAssessment(View view) {
        String debug = "saveAssessment in AssessmentDetailActivity";
        System.out.println(debug);


        String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        String dueDate = ((EditText) findViewById(R.id.dueDateEditText)).getText().toString();

        // Determine selectedAssessment type
        RadioGroup assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
        String type = ((RadioButton) findViewById(assessmentRadioGroup.getCheckedRadioButtonId())).getText().toString();
        if (type == null){ // autofill type value if it's empty
            type = "Objective Assessment";
        }


        if (selectedAssessment == null) { // if we're creating a new selectedAssessment
            int id = Assessment.assessmentArrayList.size();
            int courseId = getIntent().getIntExtra(Course.COURSE_EDIT_EXTRA, -1); // default value set to -1
            if(courseId != -1) {
                selectedAssessment = new Assessment(id, title, dueDate, type, courseId);
                sqLiteManager.addAssessmentToDatabase(selectedAssessment);
            } else {
                Toast.makeText(this, "courseId not found, setting courseId to 1", Toast.LENGTH_SHORT);
                courseId = 1;
                selectedAssessment = new Assessment(id, title, dueDate, type, courseId);
                sqLiteManager.addAssessmentToDatabase(selectedAssessment);
            }
        } else { // if we're editing an existing selectedAssessment
            selectedAssessment.setTitle(title);
            selectedAssessment.setDueDate(dueDate);
            selectedAssessment.setAssessmentType(type);

            sqLiteManager.updateAssessmentInDatabase(selectedAssessment);
        }

        // Go back to the previous activity
        finish();
    }


    public void deleteAssessment(View view) {
        if (selectedAssessment != null) {
            sqLiteManager.deleteAssessmentInDatabase(selectedAssessment.getId());
        }

        // Go back to the previous activity
        finish();
    }

    public void checkForEditAssessment() {
        // Get assessment from intent
        Intent intent = getIntent();
        int passedAssessmentId = intent.getIntExtra(Assessment.ASSESSMENT_EDIT_EXTRA, -1);
        selectedAssessment = Assessment.getAssessmentById(passedAssessmentId);

        if (selectedAssessment != null) {
            titleEditText.setText(selectedAssessment.getTitle());
            dueDateEditText.setText(selectedAssessment.getDueDate());

            String assessmentType = selectedAssessment.getAssessmentType();
            for (int i = 0; i < assessmentRadioGroup.getChildCount(); i++){
                RadioButton radioButton = (RadioButton) assessmentRadioGroup.getChildAt(i);
                if (radioButton.getText().toString().equals(assessmentType)) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }



    public void showDatePickerDialog(View view) {
    }
}

