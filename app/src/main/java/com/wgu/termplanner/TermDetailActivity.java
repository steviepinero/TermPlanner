package com.wgu.termplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class TermDetailActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText;
    private Button deleteButton;
    private SQLiteManager sqLiteManager;

    private Term selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        initWidgets();
        checkForEditTerm();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        deleteButton = findViewById(R.id.deleteTermButton);
    }

    private void checkForEditTerm() {
        Intent previousIntent = getIntent();
        int passedTermID = previousIntent.getIntExtra(Term.TERM_EDIT_EXTRA, -1);
        selectedTerm = Term.getTermForID(passedTermID);

        if (selectedTerm != null) {
            titleEditText.setText(selectedTerm.getTitle());
            startDateEditText.setText(selectedTerm.getStartDate());
            endDateEditText.setText(selectedTerm.getEndDate());
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveTerm(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = titleEditText.getText().toString();
        String startDate = startDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        if (selectedTerm == null) {
            int id = Term.termArrayList.size();
            Term newTerm = new Term(id, title, startDate, endDate);
            Term.termArrayList.add(newTerm);
            sqLiteManager.addTermToDatabase(newTerm);
        } else {
            selectedTerm.setTitle(title);
            selectedTerm.setStartDate(startDate);
            selectedTerm.setEndDate(endDate);
            sqLiteManager.updateTermInDatabase(selectedTerm);
        }

        finish();
    }

    public void deleteTerm(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        if (selectedTerm != null) {
            sqLiteManager.deleteTermInDatabase(selectedTerm.getId());
        }
        finish();
    }
}
