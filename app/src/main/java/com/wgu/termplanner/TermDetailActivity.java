package com.wgu.termplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TermDetailActivity extends AppCompatActivity {

    private EditText titleEditText, startDateEditText, endDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        initWidgets();
    }

    private void initWidgets() {

        titleEditText = findViewById(R.id.titleEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);

    }

    public void saveTerm(View view) {

        String title = String.valueOf(titleEditText.getText());
        String startDate = String.valueOf(startDateEditText.getText());
        String endDate = String.valueOf(endDateEditText.getText());

        int id = Term.termArrayList.size();
        Term newTerm = new Term(id,title, startDate, endDate);
        Term.termArrayList.add(newTerm);
        finish();

    }
}