package com.wgu.termplanner;

import static android.content.Intent.*;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Console;
import java.util.List;

public class TermAdapter extends ArrayAdapter<Term> {

    public TermAdapter(Context context, List<Term> terms) {
        super(context, 0, terms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Term term = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.term_cell, parent, false);

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView startDate = convertView.findViewById(R.id.cellStartDate);
        TextView endDate = convertView.findViewById(R.id.cellEndDate);
        Button courseDetailButton = convertView.findViewById(R.id.viewCoursesButton);
        Button termDetailButton = convertView.findViewById(R.id.editTermButton);

        title.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText((term.getEndDate()));

        courseDetailButton.setTag(term); // Set the Term object as a tag on the button
        termDetailButton.setTag(term);

        courseDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCourseDetailButtonClick(view);
            }
        });

        termDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTermDetailButtonClick(view);
            }
        });

        return convertView;
    }


    // Method to handle button click
    private void onCourseDetailButtonClick(View view) {
        String debug = "OnCourseDetailButtonClick in TermAdapter";
        System.out.println(debug);

        // Get the Term object associated with the button's parent view
        Term term = (Term) view.getTag();

        // Launch the Course List activity, passing in the termId as an extra
        Intent intent = new Intent(getContext(), CourseListActivity.class);
        intent.putExtra(Term.TERM_EDIT_EXTRA, term.getId());
        getContext().startActivity(intent);
    }

    public void onTermDetailButtonClick(View view) {
        String debug = "OnTermDetailButtonClick in TermAdapter";
        System.out.println(debug);

        // Get the Term object associated with the button's parent view
        Term term = (Term) view.getTag();

        // Launch the Course List activity, passing in the termId as an extra
        Intent intent = new Intent(getContext(), TermDetailActivity.class);
        intent.putExtra(Term.TERM_EDIT_EXTRA, term.getId());
        getContext().startActivity(intent);
    }

}

