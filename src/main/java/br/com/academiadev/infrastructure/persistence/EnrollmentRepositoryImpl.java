package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.domain.entities.Enrollment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final List<Enrollment> database = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        database.add(enrollment);
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(database);
    }

    @Override
    public List<Enrollment> findAllByStudentEmail(String email) {
        return database.stream()
                .filter(e -> e.getStudent().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByStudentAndCourse(String studentEmail, String courseTitle) {
        return database.stream()
                .anyMatch(e -> e.getStudent().getEmail().equals(studentEmail) 
                            && e.getCourse().getTitle().equals(courseTitle));
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(String studentEmail, String courseTitle) {
        return database.stream()
                .filter(e -> e.getStudent().getEmail().equals(studentEmail) 
                          && e.getCourse().getTitle().equals(courseTitle))
                .findFirst();
    }

    @Override
    public void delete(Enrollment enrollment) {
        database.remove(enrollment);
    }
}