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

    }


    private void setTermAdapter() {
        TermAdapter termAdapter = new TermAdapter(getApplicationContext(), Term.nonDeletedTerms());
        termListView.setAdapter(termAdapter);
    }

    private void setOnClickListener()
    {
        termListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Term selectedTerm = (Term) termListView.getItemAtPosition(position);
            Intent editTermIntent = new Intent(getApplicationContext(), TermDetailActivity.class);
            editTermIntent.putExtra(Term.TERM_EDIT_EXTRA, selectedTerm.getId());
            startActivity(editTermIntent);
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