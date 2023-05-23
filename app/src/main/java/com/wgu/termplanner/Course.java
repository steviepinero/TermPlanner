package com.wgu.termplanner;

import java.util.ArrayList;
import java.util.Date;

public class Course {
    public static ArrayList<Course> courseArrayList = new ArrayList<>();
    public static String COURSE_EDIT_EXTRA = "courseEdit";

    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String instructor;
    private Date deleted;
    private int termId;

    // variables for assessments and notes
    private ArrayList<Assessment> assessments;
    private String notes;

/*
    private ArrayList<Note> notes;
*/


    public Course(int id, String title, String startDate, String endDate, String status, String instructor, int termId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructor = instructor;
        this.termId = termId;

        this.assessments = new ArrayList<>();
        this.notes = notes;
    }

    public Course(int id, String title, String startDate, String endDate, String status, String instructor) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructor = instructor;
        this.termId = termId;

        this.assessments = new ArrayList<>();
        this.notes = notes;
    }

    public Course(int id) {
        this.id = id;
        this.assessments = new ArrayList<>();
        this.notes = notes;
    }

    public Course() {

    }


    public static ArrayList<Course> getCoursesForTermId(int termId) {
        ArrayList<Course> coursesForTerm = new ArrayList<>();
        for (Course course : courseArrayList) {
            if (course.getTermId() == termId) {
                coursesForTerm.add(course);
            }
        }
        return coursesForTerm;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getTermId() { return termId; }

    public void setTermId(int anInt) { this.termId = termId;}

    // Methods for assessments
    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }

    public ArrayList<Assessment> getAssessments() {
        return this.assessments;
    }

    // New methods for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
