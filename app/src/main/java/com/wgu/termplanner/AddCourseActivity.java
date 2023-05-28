package com.wgu.termplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText, instructorEditText, instructorPhoneEditText, instructorEmailEditText, noteEditText;
    private RadioGroup statusRadioGroup;
    private Button shareNoteButton;
    private int termId;
    private SQLiteManager sqLiteManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1);

        initWidgets();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        instructorEditText = findViewById(R.id.instructorEditText);
        instructorPhoneEditText = findViewById(R.id.instructorPhoneEditText);
        instructorEmailEditText = findViewById(R.id.instructorEmailEditText);
        statusRadioGroup = findViewById(R.id.statusRadioGroup);

        noteEditText = findViewById(R.id.noteEditText);
        shareNoteButton = findViewById(R.id.shareNoteButton);
        shareNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();
            }
        });


        // Initialize the SQLiteManager
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
    }

    private void shareNote() {
    }

    public void saveCourse(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String startDate = String.valueOf(startDateEditText.getText());
        String endDate = String.valueOf(endDateEditText.getText());
        String instructor = String.valueOf(instructorEditText.getText());
        String instructorPhone = String.valueOf(instructorPhoneEditText.getText());
        String instructorEmail = String.valueOf(instructorEmailEditText.getText());
        String noteContent = String.valueOf(noteEditText.getText());
        if (noteContent == null) {
            noteContent = " ";
        }

        int selectedStatusId = statusRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedStatusId);
        String status = selectedRadioButton.getText().toString();


        int id = Course.courseArrayList.size();
        Course newCourse =  new Course(id, title, startDate, endDate, status, instructor, instructorPhone, instructorEmail, noteContent, termId); // Pass termId when creating new course
        Course.courseArrayList.add(newCourse);
        sqLiteManager.addCourseToDatabase(newCourse);

        finish();
    }
}

