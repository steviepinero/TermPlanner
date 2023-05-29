package com.wgu.termplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView termListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setTermAdapter();

    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        Term.termArrayList.clear(); // Clear the list before populating

        //this was for quick testing
       /* sqLiteManager.populateWithTestData();*/

        sqLiteManager.populateTermListArray();

    }

    private void initWidgets() {
        loadFromDBToMemory();
        termListView = findViewById(R.id.termListView);
    }

    private void setTermAdapter() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        List<Term> terms = sqLiteManager.getAllTerms();

        TermAdapter termAdapter = new TermAdapter(getApplicationContext(), terms);
        termListView.setAdapter(termAdapter);
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


}
