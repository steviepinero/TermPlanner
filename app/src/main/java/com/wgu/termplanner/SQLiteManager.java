package com.wgu.termplanner;

import static android.content.ContentValues.*;

import static com.wgu.termplanner.Assessment.assessmentArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 5;

    private static final String TABLE_NAME = "Term";
    private static final String COUNTER = "Counter";
    //Term table columns
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";
/*
    private static final String DELETED_FIELD = "deleted";
*/

    //Course table columns
    private static final String COURSES_TABLE_NAME = "Courses";
    private static final String COURSE_ID_FIELD = "id";
    private static final String COURSE_TITLE_FIELD = "title";
    private static final String COURSE_START_DATE_FIELD = "startDate";
    private static final String COURSE_END_DATE_FIELD = "endDate";
    private static final String COURSE_TERM_ID_FIELD = "termId";
    private static final String COURSE_STATUS_FIELD = "status";
    private static final String COURSE_INSTRUCTOR_FIELD = "instructor";

    //Assessment table columns
    private static final String ASSESSMENTS_TABLE_NAME = "Assessments";
    private static final String ASSESSMENT_ID_FIELD = "id";
    private static final String ASSESSMENT_TITLE_FIELD = "title";
    private static final String ASSESSMENT_DUE_DATE_FIELD = "dueDate";
    private static final String ASSESSMENT_TYPE_FIELD = "type";
    private static final String ASSESSMENT_COURSE_ID_FIELD = "courseId";

    //Note table columns
    private static final String NOTES_TABLE_NAME = "Notes";
    private static final String NOTE_ID_FIELD = "id";
    private static final String NOTE_CONTENT_FIELD = "content";
    private static final String NOTE_COURSE_ID_FIELD = "courseId";


    //formatting the date with timestamp
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    private int deleted;


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder termSql;
        termSql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(START_DATE_FIELD)
                .append(" TEXT, ")
                .append(END_DATE_FIELD)
                /*.append(" TEXT, ")
                .append(DELETED_FIELD)*/
                .append( " TEXT)");

        sqLiteDatabase.execSQL(termSql.toString());

        StringBuilder courseSql;
        courseSql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(COURSES_TABLE_NAME)
                .append("(")
                .append(COURSE_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COURSE_TITLE_FIELD)
                .append(" TEXT, ")
                .append(COURSE_START_DATE_FIELD)
                .append(" TEXT, ")
                .append(COURSE_END_DATE_FIELD)
                .append(" TEXT, ")
                .append(COURSE_INSTRUCTOR_FIELD)
                .append(" TEXT, ")
                .append(COURSE_STATUS_FIELD)
                .append(" TEXT, ")
                .append(COURSE_TERM_ID_FIELD)
                .append(" INT, ")
                .append(" FOREIGN KEY(")
                .append(COURSE_TERM_ID_FIELD)
                .append(") REFERENCES ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(")")
                .append(")");

        sqLiteDatabase.execSQL(courseSql.toString());

        StringBuilder assessmentSql;
        assessmentSql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(ASSESSMENTS_TABLE_NAME)
                .append("(")
                .append(ASSESSMENT_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ASSESSMENT_TITLE_FIELD)
                .append(" TEXT, ")
                .append(ASSESSMENT_DUE_DATE_FIELD)
                .append(" TEXT, ")
                .append(ASSESSMENT_TYPE_FIELD)
                .append(" TEXT, ")
                .append(ASSESSMENT_COURSE_ID_FIELD)
                .append(" INT, ")
                .append(" FOREIGN KEY(")
                .append(ASSESSMENT_COURSE_ID_FIELD)
                .append(") REFERENCES ")
                .append(COURSES_TABLE_NAME)
                .append("(")
                .append(COURSE_ID_FIELD)
                .append(")")
                .append(")");

        sqLiteDatabase.execSQL(assessmentSql.toString());

        StringBuilder noteSql;
        noteSql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(NOTES_TABLE_NAME)
                .append("(")
                .append(NOTE_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOTE_CONTENT_FIELD)
                .append(" TEXT, ")
                .append(NOTE_COURSE_ID_FIELD)
                .append(" INT, ")
                .append(" FOREIGN KEY(")
                .append(NOTE_COURSE_ID_FIELD)
                .append(") REFERENCES ")
                .append(COURSES_TABLE_NAME)
                .append("(")
                .append(COURSE_ID_FIELD)
                .append(")")
                .append(")");

        sqLiteDatabase.execSQL(noteSql.toString());


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ASSESSMENTS_TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }


    public void addTermToDatabase(Term term) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, term.getId());
        contentValues.put(TITLE_FIELD, term.getTitle());
        contentValues.put(START_DATE_FIELD, term.getStartDate());
        contentValues.put(END_DATE_FIELD, term.getEndDate());
      /*  contentValues.put(DELETED_FIELD, getStringFromDate(term.getDeleted()));*/

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);


    }

    public void populateTermListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0)
            {
                while(result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String startDate = result.getString(3);
                    String endDate = result.getString(4);
                    /*String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);*/

                    //converts strings to date
                    Date startDateRefactor = getDateFromString(startDate);
                    Date endDateRefactor = getDateFromString(endDate);

                    Term term = new Term(id, title, startDate, endDate);
                    Term.termArrayList.add(term);

                }
            }
        }
    }

    public void updateTermInDatabase(Term term)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, term.getId());
        contentValues.put(TITLE_FIELD, term.getTitle());
        contentValues.put(START_DATE_FIELD, term.getStartDate());
        contentValues.put(END_DATE_FIELD, term.getEndDate());
/*
        contentValues.put(DELETED_FIELD, getStringFromDate(term.getDeleted()));
*/

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(term.getId())});
    }

    private String getStringFromDate(Date date) {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string) {
        try{
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e) {
            return null;
        }
    }

    public void addCourseToDatabase(Course course) {
        SQLiteDatabase db = sqLiteManager.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Assign values to the columns
/*
        values.put(COURSE_ID_FIELD, course.getId());
*/
        values.put(COURSE_TITLE_FIELD, course.getTitle());
        values.put(COURSE_START_DATE_FIELD, course.getStartDate());
        values.put(COURSE_END_DATE_FIELD, course.getEndDate());
        values.put(COURSE_INSTRUCTOR_FIELD, course.getInstructor());
        values.put(COURSE_STATUS_FIELD, course.getStatus());
        values.put(COURSE_TERM_ID_FIELD, course.getTermId());



        // Insert the values into the database
        db.insert(COURSES_TABLE_NAME, null, values);
        db.close();
    }

    public void populateCourseListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME, null)) {
            if(result.getCount() != 0)
            {
                while(result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String startDate = result.getString(3);
                    String endDate = result.getString(4);
                    String instructor = result.getString(5);
                    String status = result.getString(6);
/*
                    int termId = result.getInt(7);
*/

                    Date startDateRefactor = getDateFromString(startDate);
                    Date endDateRefactor = getDateFromString(endDate);

                    Course course = new Course(id, title, startDate, endDate, instructor, status);
                    Course.courseArrayList.add(course);
                }
            }
        }
    }

    public Course getCourseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + COURSES_TABLE_NAME + " WHERE " + COURSE_TERM_ID_FIELD + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        // Create a Course object from the cursor
        Course course = new Course(id);
        course.setId(cursor.getInt(0));
        course.setTitle(cursor.getString(1));
        course.setStartDate(cursor.getString(2));
        course.setEndDate(cursor.getString(3));
        course.setInstructor(cursor.getString(4));
        course.setStatus(cursor.getString(5));
        course.setTermId(cursor.getInt(6));

        cursor.close();
        return course;
    }


    public ArrayList<Assessment> getAssessmentsForCourseId(int courseId) {
        ArrayList<Assessment> assessmentsForCourse = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                "assessments", // table to query
                new String[] {"id", "title", "dueDate", "type", "courseId"}, // columns to return
                "courseId = ?", // columns for the WHERE clause
                new String[] {String.valueOf(courseId)}, // values for the WHERE clause
                null, // group rows
                null, // filter row groups
                null  // sort order
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String assessmentType = cursor.getString(3);
                int course_id = cursor.getInt(4);

                assessmentsForCourse.add(new Assessment(id, title, dueDate, assessmentType, course_id));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return assessmentsForCourse;
    }

    public void updateCourseInDatabase(Course selectedCourse) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Assign values to the columns
        contentValues.put(COURSE_ID_FIELD, selectedCourse.getId());
        contentValues.put(COURSE_TITLE_FIELD, selectedCourse.getTitle());
        contentValues.put(COURSE_START_DATE_FIELD, selectedCourse.getStartDate());
        contentValues.put(COURSE_END_DATE_FIELD, selectedCourse.getEndDate());
        contentValues.put(COURSE_INSTRUCTOR_FIELD, selectedCourse.getInstructor());
        contentValues.put(COURSE_STATUS_FIELD, selectedCourse.getStatus());

        contentValues.put(COURSE_TERM_ID_FIELD, selectedCourse.getTermId());


        sqLiteDatabase.update(COURSES_TABLE_NAME, contentValues, COURSE_ID_FIELD + " =? ", new String[]{String.valueOf(selectedCourse.getId())});


    }

    public ArrayList<Course> getCoursesForTermId(int termId) {
        ArrayList<Course> courses = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + COURSES_TABLE_NAME + " WHERE termId = ?";

        Cursor cursor = db.query(
                "courses", // table to query
                new String[] {"id", "title", "startDate", "endDate", "instructor", "status","termId"}, // columns to return
                "termId = ?", // columns for the WHERE clause
                new String[] {String.valueOf(termId)}, // values for the WHERE clause
                null, // group rows
                null, // filter row groups
                null  // sort order
        );

        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String startDate = cursor.getString(2);
                String endDate = cursor.getString(3);
                String instructorName = cursor.getString(4);
                String status = cursor.getString(5);
                int term_Id = cursor.getInt(6);

                courses.add(new Course(id, title, startDate, endDate, instructorName, status, term_Id));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return courses;
    }


    public void addAssessmentToDatabase(Assessment assessment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ASSESSMENT_TITLE_FIELD, assessment.getTitle());
        contentValues.put(ASSESSMENT_DUE_DATE_FIELD, assessment.getDueDate());
        contentValues.put(ASSESSMENT_TYPE_FIELD, assessment.getAssessmentType());
        contentValues.put(ASSESSMENT_COURSE_ID_FIELD, assessment.getCourseId());

        sqLiteDatabase.insert(ASSESSMENTS_TABLE_NAME, null, contentValues);
    }

    public void updateAssessmentInDatabase(Assessment assessment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ASSESSMENT_TITLE_FIELD, assessment.getTitle());
        contentValues.put(ASSESSMENT_DUE_DATE_FIELD, assessment.getDueDate());
        contentValues.put(ASSESSMENT_TYPE_FIELD, assessment.getAssessmentType());
        contentValues.put(ASSESSMENT_COURSE_ID_FIELD, assessment.getCourseId());

        sqLiteDatabase.update(ASSESSMENTS_TABLE_NAME, contentValues, ASSESSMENT_ID_FIELD + " =? ", new String[]{String.valueOf(assessment.getId())});
    }

    public void deleteAssessmentInDatabase(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(ASSESSMENTS_TABLE_NAME, ASSESSMENT_ID_FIELD + " =? ", new String[]{String.valueOf(id)});
    }

    public static Assessment getAssessmentById(int id) {
        for (Assessment assessment : assessmentArrayList) {
            if (assessment.getId() == id) {
                return assessment;
            }
        }
        return null; // or throw an exception 10-21-2023 if course is not found
    }

    public void populateAssessmentListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + ASSESSMENTS_TABLE_NAME, null)) {
            if(result.getCount() != 0)
            {
                while(result.moveToNext())
                {
                    int id = result.getInt(0);
                    String title = result.getString(1);
                    String dueDate = result.getString(2);
                    String assessmentType = result.getString(3);
                    int courseId = result.getInt(4);

                    Assessment assessment = new Assessment(id, title, dueDate, assessmentType, courseId);
                    Assessment.assessmentArrayList.add(assessment);
                }
            }
        }
    }


    public void addNoteToDatabase(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOTE_CONTENT_FIELD, note.getContent());
        contentValues.put(NOTE_COURSE_ID_FIELD, note.getCourseId());

        sqLiteDatabase.insert(NOTES_TABLE_NAME, null, contentValues);
    }

    public void updateNoteInDatabase(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOTE_CONTENT_FIELD, note.getContent());
        contentValues.put(NOTE_COURSE_ID_FIELD, note.getCourseId());

        sqLiteDatabase.update(NOTES_TABLE_NAME, contentValues, NOTE_ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
    }

    public void deleteNoteInDatabase(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(NOTES_TABLE_NAME, NOTE_ID_FIELD + " =? ", new String[]{String.valueOf(id)});
    }

}
