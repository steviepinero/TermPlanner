package com.wgu.termplanner;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class Assessment {
    public static ArrayList<Assessment> assessmentArrayList = new ArrayList<>();
    public static String ASSESSMENT_EDIT_EXTRA = "assessmentEdit";

    private int    id;
    private String title;
    private String dueDate;
    private String assessmentType;
    private int    courseId;




    public Assessment(int id, String title, String dueDate, String assessmentType, int courseId) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public Assessment(int id, String title, String dueDate, String assessmentType) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public Assessment(Cursor cursor) {
    }

    public Assessment(String title, String dueDate, String assessmentType, int courseId) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public Assessment(int courseId) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }


    public static ArrayList<Assessment> getAssessmentsForCourseId(int courseId) {
        ArrayList<Assessment> assessmentsForCourse = new ArrayList<>();
        for (Assessment assessment : assessmentArrayList) {
            if (assessment.getCourseId() == courseId) {
                assessmentsForCourse.add(assessment);
            }
        }
        return assessmentsForCourse;



    }

    public static Assessment getAssessmentById(int passedAssessmentId) {

            for (Assessment assessment : assessmentArrayList)
            {
                if(assessment.getId() == passedAssessmentId)
                    return assessment;
            }
            return null;

    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
}
