package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
        Term.termArrayList.clear(); // Clear the list before populating
        sqLiteManager.populateTermListArray();

    }

    private void initWidgets() {
        SQLiteManager dbManager = SQLiteManager.instanceOfDatabase(this);
        //TODO REMOVE TEST DATA INIT BEFORE SUBMISSION
        dbManager.populateWithTestData();

        termListView = findViewById(R.id.termListView);
    }

    private void setTermAdapter() {
        TermAdapter termAdapter = new TermAdapter(getApplicationContext(), Term.nonDeletedTerms());
        termListView.setAdapter(termAdapter);
    }

    private void setOnClickListener() {
        termListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Term selectedTerm = (Term) termListView.getItemAtPosition(position);
            Intent termDetailIntent = new Intent(getApplicationContext(), TermDetailActivity.class);
            termDetailIntent.putExtra(Term.TERM_EDIT_EXTRA, selectedTerm.getId());
            startActivity(termDetailIntent);
        });
    }

    public void newTerm(View view) {
        Intent newTermIntent = new Intent(this, TermDetailActivity.class);
        startActivity(newTermIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTermAdapter();
    }

    /*public void onTermDetailButtonClick(View view) {
        String debug = "OnTermDetailButtonClick in MainActivity";
        System.out.println(debug);

        // Get the Term object associated with the button's parent view
        Term term = (Term) view.getTag();

        // Launch the Course List activity, passing in the termId as an extra
        Intent intent = new Intent(getApplicationContext(), TermDetailActivity.class);
        intent.putExtra(Term.TERM_EDIT_EXTRA, term.getId());
        startActivity(intent);
    }*/
}
