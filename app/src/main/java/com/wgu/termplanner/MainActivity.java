package com.wgu.termplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView termListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setTermAdapter();
        setOnClickListener();



    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateTermListArray();
    }

    private void initWidgets() {
        termListView = findViewById(R.id.termListView);
        setOnClickListener();

        Button courseDetailButton = findViewById(R.id.courseDetailButton);
        if (courseDetailButton != null) {
            courseDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle button click event
/*
                    onCourseDetailButtonClick(view);
*/
                }
            });
        }
    }

/*
    public void onCourseDetailButtonClick(View view) {
        // Get the course ID from the intent extras
        long courseId = getIntent().getLongExtra(Course.COURSE_EDIT_EXTRA, -1);
        if (courseId == -1) {
            // The course ID was not found in the intent extras
            Toast.makeText(this, "Course ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Launch the CourseDetailActivity and pass the course ID as an extra
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(Course.COURSE_EDIT_EXTRA, courseId);
        startActivity(intent);
    }
*/

    private void setTermAdapter() {
        TermAdapter termAdapter = new TermAdapter(getApplicationContext(), Term.nonDeletedTerms());
        termListView.setAdapter(termAdapter);
    }

    private void setOnClickListener()
    {
        termListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Term selectedTerm = (Term) termListView.getItemAtPosition(position);
                Intent editTermIntent = new Intent(getApplicationContext(), TermDetailActivity.class);
                editTermIntent.putExtra(Term.TERM_EDIT_EXTRA, selectedTerm.getId());
                startActivity(editTermIntent);
            }
        });
    }

    public void newTerm(View view) {

        Intent newTermIntent = new Intent(this, TermDetailActivity.class);
        startActivity(newTermIntent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setTermAdapter();
    }
}