package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseRepositoryImpl implements CourseRepository {

    private final Map<String, Course> database = new HashMap<>();

    @Override
    public void save(Course course) {
        database.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(database.get(title));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(database.values());
    }
}