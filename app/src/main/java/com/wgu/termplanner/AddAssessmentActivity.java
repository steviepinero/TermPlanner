package com.wgu.termplanner;

import android.app.DatePickerDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        courseId = getIntent().getIntExtra(Course.COURSE_EDIT_EXTRA, -1);

        initWidgets();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        dueDateEditText = findViewById(R.id.endDateEditText);
        assessmentRadioGroup = findViewById(R.id.assessmentRadioGroup);
//TODO FIX THE DATE PICKER & IMPLEMENT IT FOR EACH EDIT VIEW
        if (dueDateEditText != null) {
            dueDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("AddAssessmentActivity", "dueDateEditText onClick called");
                    Log.println(Log.INFO, "AddAssessmentActivity","dueDateEditText onClick called");


                    final Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssessmentActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                    month = month + 1; //Month in DatePickerDialog starts with 0 for January
                                    String date = dayOfMonth + "/" + month + "/" + year;
                                    dueDateEditText.setText(date);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
            });
        } else {
            Toast.makeText(AddAssessmentActivity.this, "Error initializing date field", Toast.LENGTH_SHORT).show();

        }
    }

    public void saveAssessment(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String endDate = String.valueOf(dueDateEditText.getText());

        int selectedTypeId = assessmentRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedTypeId);
        String type = selectedRadioButton.getText().toString();

        int id = Assessment.assessmentArrayList.size();
        Assessment newAssessment = new Assessment(id, title, endDate, type, courseId);
        Assessment.assessmentArrayList.add(newAssessment);
        sqLiteManager.addAssessmentToDatabase(newAssessment);

        finish();
    }
}

