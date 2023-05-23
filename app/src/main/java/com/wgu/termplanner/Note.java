package com.wgu.termplanner;

import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    private int id;
    private String content;
    private int courseId;

    public Note(int id, String content, int courseId) {
        this.id = id;
        setContent(content);
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public static ArrayList<Note> getNotesForCourseId(int courseId) {
        ArrayList<Note> notesForCourse = new ArrayList<>();
        for (Note note : noteArrayList) {
            if (note.getCourseId() == courseId) {
                notesForCourse.add(note);
            }
        }
        return notesForCourse;
    }
}
