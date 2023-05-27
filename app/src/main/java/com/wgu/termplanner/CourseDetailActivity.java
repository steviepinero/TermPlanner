package com.wgu.termplanner;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CourseDetailActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText, instructorEditText;
    private RadioGroup statusRadioGroup;
    private Course selectedCourse;
    private RecyclerView assessmentRecyclerView;
    private AssessmentAdapter assessmentAdapter;
    private SQLiteManager sqLiteManager;
    private EditText noteEditText;
    private Button shareNoteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initWidgets();
        checkForEditCourse();

        if (selectedCourse == null) {
            Toast.makeText(this, "selected course is not found - null", Toast.LENGTH_SHORT).show();
        } else {
            noteEditText = findViewById(R.id.noteEditText);

            assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
            assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            ArrayList<Assessment> assessments = sqLiteManager.getAssessmentsForCourseId(selectedCourse.getId());

            assessmentAdapter = new AssessmentAdapter(this, assessments);
            assessmentRecyclerView.setAdapter(assessmentAdapter);
            //notification channel required
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("COURSE_CHANNEL_ID", "Course Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Channel for Course Notifications");
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }

    }


    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        instructorEditText = findViewById(R.id.instructorEditText);
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

    private void checkForEditCourse() {
        Intent previousIntent = getIntent();
        int passedCourseID = previousIntent.getIntExtra(Course.COURSE_EDIT_EXTRA, -1);
        selectedCourse = sqLiteManager.getCourseById(passedCourseID);

        if (selectedCourse != null) {
            titleEditText.setText(selectedCourse.getTitle());
            startDateEditText.setText(selectedCourse.getStartDate());
            endDateEditText.setText(selectedCourse.getEndDate());
            instructorEditText.setText(selectedCourse.getInstructor());
            noteEditText.setText(selectedCourse.getNote());

            }


            String status = selectedCourse.getStatus();
            for (int i = 0; i < statusRadioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) statusRadioGroup.getChildAt(i);
                if (radioButton.getText().toString().equals(status)) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }


    public void saveCourse(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String startDate = String.valueOf(startDateEditText.getText());
        String endDate = String.valueOf(endDateEditText.getText());
        String instructor = String.valueOf(instructorEditText.getText());
        String noteContent = String.valueOf(noteEditText.getText());



        int selectedStatusId = statusRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedStatusId);
        String status = selectedRadioButton.getText().toString();

        int termId = getIntent().getIntExtra(Term.TERM_EDIT_EXTRA, -1); // Retrieve termId from intent

        if (selectedCourse == null) {
            int id = Course.courseArrayList.size();
            selectedCourse = new Course(id, title, startDate, endDate, status, instructor, noteContent, termId); // Pass termId when creating new course
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");

        Date firstDate = null;
        Date lastDate = null;

        try {
            // Parse the string dates into Date objects
            firstDate = sdf.parse(startDate);
            lastDate = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Convert the Date objects to milliseconds
        long firstTimeInMillis = firstDate.getTime();
        long lastTimeInMillis = lastDate.getTime();

        System.out.println("Start date in milliseconds: " + firstTimeInMillis);
        System.out.println("End date in milliseconds: " + lastTimeInMillis);

        // Schedule alarms for the start and end dates of the course
        scheduleAlarm(firstTimeInMillis, selectedCourse.getTitle() + " is starting today!");
        scheduleAlarm(lastTimeInMillis, selectedCourse.getTitle() + " is ending today!");


        finish();
    }


    public void deleteCourse(View view) {
        selectedCourse.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateCourseInDatabase(selectedCourse);
        finish();
    }



    public void shareNote() {
       /* if (selectedCourse != null) {
            ArrayList<Note> courseNotes = sqLiteManager.getNotesForCourseId(selectedCourse.getId());
            if (!courseNotes.isEmpty()) {
                Note note = courseNotes.get(0);
                String noteContent = note.getContent();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, noteContent);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        } else {
            Toast.makeText(this, "No course selected", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void scheduleAlarm(long time, String courseName) {
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        intent.setAction(MyAlarmReceiver.ACTION);
        intent.putExtra("courseName", courseName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Getting the alarm manager service
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Setting the alarm. This will be triggered once at the specified date and time
        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }




}
