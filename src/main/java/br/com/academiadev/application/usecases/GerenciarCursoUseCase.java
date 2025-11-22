package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

public class GerenciarCursoUseCase {
    private final CourseRepository repository;

    public GerenciarCursoUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public void toggleStatus(String courseTitle) {
        Course course = repository.findByTitle(courseTitle)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        
        if (course.isActive()) {
            course.inactivate();
        } else {
            course.activate();
        }

        repository.save(course);
    }
}