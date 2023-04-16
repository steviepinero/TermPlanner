package com.wgu.termplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView termListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setTermAdapter();
    }

    private void initWidgets() {
        termListView = findViewById(R.id.termListView);
    }

    private void setTermAdapter() {
        TermAdapter termAdapter = new TermAdapter(getApplicationContext(), Term.termArrayList);
        termListView.setAdapter(termAdapter);
    }


    public void newTerm(View view) {

        Intent newTermIntent = new Intent(this, TermDetailActivity.class);
        startActivity(newTermIntent);

    }
}