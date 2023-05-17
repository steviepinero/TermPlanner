package com.wgu.termplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> assessments;
    private Context context;

    public AssessmentAdapter(Context context, List<Assessment> assessments) {
        this.context = context;
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_cell, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);

        holder.title.setText(assessment.getTitle());
        holder.endDate.setText(assessment.getDueDate());
        holder.assessmentType.setText(assessment.getAssessmentType());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {

        TextView title, endDate, assessmentType;  // I'm just so tired...

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cellTitle);
            endDate = itemView.findViewById(R.id.cellEndDate);
            assessmentType = itemView.findViewById(R.id.cellAssessmentType);
        }
    }
}

