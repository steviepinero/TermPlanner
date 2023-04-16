package com.wgu.termplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Term";
    private static final String COUNTER = "Counter";

    //Term table columns
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";

    //formatting the date
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");


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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
