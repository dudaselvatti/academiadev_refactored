package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import java.util.List;

public class ListarCursosUseCase {
    private final CourseRepository repository;

    public ListarCursosUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> execute() {
        return repository.findAll();
    }
}