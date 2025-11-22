package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;

public class AtualizarProgressoUseCase {
    private final EnrollmentRepository repository;

    public AtualizarProgressoUseCase(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public void execute(String studentEmail, String courseTitle, int newProgress) {
        Enrollment enrollment = repository.findByStudentAndCourse(studentEmail, courseTitle)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada neste curso."));
        
        enrollment.updateProgress(newProgress);
        repository.save(enrollment);
    }
}