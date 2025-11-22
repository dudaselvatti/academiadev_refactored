package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.Enrollment;
import java.util.List;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findAll();
    List<Enrollment> findAllByStudentEmail(String email);
    boolean existsByStudentAndCourse(String studentEmail, String courseTitle);
}