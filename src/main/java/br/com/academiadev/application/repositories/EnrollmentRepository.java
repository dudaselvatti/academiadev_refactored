package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.Enrollment;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findAll();
    List<Enrollment> findAllByStudentEmail(String email);
    boolean existsByStudentAndCourse(String studentEmail, String courseTitle);
    Optional<Enrollment> findByStudentAndCourse(String studentEmail, String courseTitle);
    void delete(Enrollment enrollment);
}