package com.example.finalwmp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnrollmentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEnrolledSubjects;
    private Button buttonSubmit;
    private ArrayList<Subject> selectedSubjects;
    private SubjectAdapter subjectAdapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        firestore = FirebaseFirestore.getInstance();

        recyclerViewEnrolledSubjects = findViewById(R.id.recyclerViewEnrolledSubjects);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        selectedSubjects = new ArrayList<>();
        selectedSubjects.add(new Subject("Academic Writing", 3));
        selectedSubjects.add(new Subject("Wireless and Mobile Programming", 3));
        selectedSubjects.add(new Subject("Network Security", 3));
        selectedSubjects.add(new Subject("Software Engineering", 3));
        selectedSubjects.add(new Subject("Numerical Methods", 3));
        selectedSubjects.add(new Subject("Artificial Intelligence", 3));
        selectedSubjects.add(new Subject("Data Structure and Algorithm", 3));
        selectedSubjects.add(new Subject("3D Computer Graphics and Animation", 3));
        selectedSubjects.add(new Subject("Calculus", 3));

        recyclerViewEnrolledSubjects.setLayoutManager(new LinearLayoutManager(this));

        subjectAdapter = new SubjectAdapter(selectedSubjects);
        recyclerViewEnrolledSubjects.setAdapter(subjectAdapter);

        buttonSubmit.setOnClickListener(v -> {
            saveSelectedSubjectsToFirestore();
//            navigateToSummaryActivity();
        });
    }

    private void saveSelectedSubjectsToFirestore() {

        SharedPrefManager spm = new SharedPrefManager(this);
        String userId = spm.getEmail();
        EnrolmentManager em = new EnrolmentManager();

        WriteBatch batch = firestore.batch();

        List<Subject> selectedData = subjectAdapter.getSelectedSubjects();
        em.getUserEnrollments(userId).thenAccept(enrollment -> {

            for(Subject subject : selectedData) {
                boolean subjectExist = false;
                for (Subject s : enrollment) {
                    if (s.getSubjectName().equals(subject.getSubjectName())) {
                        subjectExist = true;
                        break;
                    }
                }

                if(subjectExist) {
                    continue;
                }

                DocumentReference userEnrollmentRef = firestore.collection("enrollments").document();
                HashMap<String, Object> selected = new HashMap<>();
                selected.put("userId", userId);
                selected.put("subjectName", subject.getSubjectName());
                selected.put("credits", subject.getCredits());
                selected.put("isSelected", subject.isSelected());
                batch.set(userEnrollmentRef, selected);
            }


            batch.commit().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    navigateToSummaryActivity();
                    Toast.makeText(this, "Subjects saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to save subjects " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void navigateToSummaryActivity() {
        Intent intent = new Intent(this, EnrollmentSummaryActivity.class);
        startActivity(intent);
    }
}
