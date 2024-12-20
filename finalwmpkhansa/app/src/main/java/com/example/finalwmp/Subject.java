package com.example.finalwmp;

public class Subject {
    private String subjectName;
    private int credits;
    private boolean isSelected;

    public Subject(String subjectName, int credits) {
        this.subjectName = subjectName;
        this.credits = credits;
        this.isSelected = false; // Initially not selected
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
