package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;

public class CancelarMatriculaUseCase {
    private final EnrollmentRepository repository;

    public CancelarMatriculaUseCase(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public void execute(String studentEmail, String courseTitle) {
        Enrollment enrollment = repository.findByStudentAndCourse(studentEmail, courseTitle)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada."));
        
        repository.delete(enrollment);

        enrollment.getStudent().getEnrollments().remove(enrollment);
    }
}