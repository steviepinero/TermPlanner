package com.wgu.termplanner;

import static android.content.ContentValues.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 2;

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


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
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

    //TODO fix course not showing in list
    public void addCourseToDatabase(Course course) {
        SQLiteDatabase db = getWritableDatabase();
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

        //might cause an issue bc of getId() method call
        contentValues.put(COURSE_TERM_ID_FIELD, selectedCourse.getTermId());


        sqLiteDatabase.update(COURSES_TABLE_NAME, contentValues, COURSE_ID_FIELD + " =? ", new String[]{String.valueOf(selectedCourse.getId())});


    }
}
