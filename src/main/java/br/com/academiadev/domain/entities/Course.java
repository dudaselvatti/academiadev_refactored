package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;

public class Course {
    private String title;
    private String description;
    private String instructorName;
    private int durationInHours;
    private DifficultyLevel difficultyLevel;
    private CourseStatus status;

    public Course(String title, String description, String instructorName, int durationInHours, DifficultyLevel difficultyLevel) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("O título do curso não pode ser vazio.");
        }
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.status = CourseStatus.ACTIVE;
    }

    public void inactivate() {
        this.status = CourseStatus.INACTIVE;
    }

    public void activate() {
        this.status = CourseStatus.ACTIVE;
    }
    
    public boolean isActive() {
        return this.status == CourseStatus.ACTIVE;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructorName() { return instructorName; }
    public int getDurationInHours() { return durationInHours; }
    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public CourseStatus getStatus() { return status; }

    @Override
    public String toString() {
        return title + " (" + difficultyLevel + ") - " + status;
    }
}