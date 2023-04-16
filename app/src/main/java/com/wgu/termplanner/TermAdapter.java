package com.wgu.termplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        title.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText((term.getEndDate()));

        return convertView;
    }

}
