package com.example.finalwmp;

import java.util.ArrayList;

public class Enrollment {

    private ArrayList<Subject> selectedSubjects;

    public Enrollment(ArrayList<Subject> selectedSubjects) {
        this.selectedSubjects = selectedSubjects;
    }

    public ArrayList<Subject> getSelectedSubjects() {
        return selectedSubjects;
    }

    public void setSelectedSubjects(ArrayList<Subject> selectedSubjects) {
        this.selectedSubjects = selectedSubjects;
    }
}
