package br.com.academiadev.domain.entities;

import java.time.LocalDateTime;

public class Enrollment {
    private Student student;
    private Course course;
    private int progress;
    private LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.progress = 0;
        this.enrollmentDate = LocalDateTime.now();
    }

    public void updateProgress(int newProgress) {
        if (newProgress < 0 || newProgress > 100) {
            throw new IllegalArgumentException("Progresso deve ser entre 0 e 100");
        }
        this.progress = newProgress;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
}