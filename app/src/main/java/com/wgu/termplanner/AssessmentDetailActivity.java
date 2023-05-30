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

import java.text.ParseException;
import java.util.Date;

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
        //notification channel required
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ASSESSMENT_CHANNEL_ID", "Assessment Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for Assessment Notifications");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

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

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");

        Date date = null;


        try {
            // Parse the string dates into Date objects
            date = sdf.parse(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Convert the Date objects to milliseconds
        long timeInMillis = date.getTime();


        System.out.println("End date in milliseconds: " + timeInMillis);

        // Schedule alarms for the start and end dates of the course
        scheduleAlarm(timeInMillis, selectedAssessment.getTitle(), "assessment");

        // Go back to the previous activity
        finish();
    }

    private void scheduleAlarm(long time, String assessmentName, String type) {
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        intent.setAction(MyAlarmReceiver.ACTION);
        intent.putExtra("assessmentName", assessmentName);
        intent.putExtra(MyAlarmReceiver.TYPE_EXTRA, type);  // notification type parameter
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Getting the alarm manager service
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Setting the alarm. This will be triggered once at the specified date and time
        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        System.out.println("ScheduleAlarm: AssessmentDetailActivity -  " + assessmentName + "\n Alarm details: " + am);
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

