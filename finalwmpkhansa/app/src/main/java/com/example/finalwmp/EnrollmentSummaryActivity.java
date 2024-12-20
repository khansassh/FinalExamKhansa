package com.example.finalwmp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EnrollmentSummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSelectedSubjects;
    private TextView textViewHeader;
    private TextView textTotalCredits;
    private Button buttonBack; // Add back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary);

        // Initialize views
        recyclerViewSelectedSubjects = findViewById(R.id.recyclerViewSelectedSubjects);
        textViewHeader = findViewById(R.id.selectedSubjectsHeader);
        textTotalCredits = findViewById(R.id.textTotalCredits);
        buttonBack = findViewById(R.id.buttonBack);  // Back button

        // Set up back button action
        buttonBack.setOnClickListener(v -> {
            // Navigate to LoginActivity
            Intent intent = new Intent(EnrollmentSummaryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Optional: Close current activity to prevent returning to it
        });

        // Fetch enrollment data
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        EnrolmentManager em = new EnrolmentManager();

        em.getUserEnrollments(sharedPrefManager.getEmail()).thenAccept(subjects -> {
            if (subjects != null && !subjects.isEmpty()) {
                // Set up RecyclerView with SubjectAdapter
                recyclerViewSelectedSubjects.setLayoutManager(new LinearLayoutManager(this));
                SubjectAdapter adapter = new SubjectAdapter(new ArrayList<>(subjects));
                recyclerViewSelectedSubjects.setAdapter(adapter);

                // Calculate total credits
                int totalCredits = 0;
                for (Subject subject : subjects) {
                    totalCredits += subject.getCredits();  // Assuming you have a getCredits method in Subject class
                }

                // Set the total credits
                textTotalCredits.setText(String.valueOf(totalCredits));

                textViewHeader.setText("Selected Subjects:");
            } else {
                textViewHeader.setText("No subjects selected.");
                Toast.makeText(this, "No subjects enrolled.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
