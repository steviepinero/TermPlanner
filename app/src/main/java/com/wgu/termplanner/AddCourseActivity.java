package com.wgu.termplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText;
    private Button saveButton;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initWidgets();
        retrieveTermId();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse();
            }
        });
    }

    private void retrieveTermId() {
        termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1);
    }

    private void saveCourse() {
        String title = titleEditText.getText().toString();
        String startDate = startDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Course newCourse = new Course(termId, title, startDate, endDate);
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.addCourseToDatabase(newCourse);

        Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
