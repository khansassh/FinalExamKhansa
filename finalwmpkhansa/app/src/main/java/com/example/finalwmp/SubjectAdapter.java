package com.example.finalwmp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private ArrayList<Subject> subjectList;
    private ArrayList<Subject> selectedSubjects = new ArrayList<>();
    private int totalCredits = 0;
    private static final int MAX_CREDITS = 24;

    public SubjectAdapter(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.textViewSubjectName.setText(subject.getSubjectName());
        holder.textViewCredits.setText("Credits: " + subject.getCredits());
        holder.checkBox.setChecked(selectedSubjects.contains(subject));

        // Set the checkbox listener
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Check if the total credits would exceed the limit
                if (totalCredits + subject.getCredits() <= MAX_CREDITS) {
                    selectedSubjects.add(subject);
                    totalCredits += subject.getCredits();
                } else {
                    // Uncheck the checkbox and notify the user
                    buttonView.setChecked(false);
                    // Optional: You can show a message to inform the user
                    // Toast.makeText(holder.itemView.getContext(), "Maximum credits reached", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If unchecked, remove the subject and reduce the total credits
                selectedSubjects.remove(subject);
                totalCredits -= subject.getCredits();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    // Add this method to get the list of selected subjects
    public ArrayList<Subject> getSelectedSubjects() {
        return selectedSubjects;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubjectName, textViewCredits;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubjectName = itemView.findViewById(R.id.subjectNameTextView);
            textViewCredits = itemView.findViewById(R.id.creditsTextView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
