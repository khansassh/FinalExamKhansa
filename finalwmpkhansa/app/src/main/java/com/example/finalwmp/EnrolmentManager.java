package com.example.finalwmp;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class EnrolmentManager {

    public EnrolmentManager() {}
    public CompletableFuture<List<Subject>> getUserEnrollments(String email) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CompletableFuture<List<Subject>> future = new CompletableFuture<>();

        database.collection("enrollments")
                .whereEqualTo("userId", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Subject> subjects = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            String subjectName = document.getString("subjectName");
                            int credits = Objects.requireNonNull(document.getDouble("credits")).intValue();
                            boolean isSelected = Boolean.TRUE.equals(document.getBoolean("isSelected"));
                            Subject subject = new Subject(subjectName, credits);
                            subject.setSelected(isSelected);
                            subjects.add(subject);
                        }
                        future.complete(subjects);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
