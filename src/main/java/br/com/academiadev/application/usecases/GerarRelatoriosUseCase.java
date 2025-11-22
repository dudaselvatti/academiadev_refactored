package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GerarRelatoriosUseCase {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public GerarRelatoriosUseCase(CourseRepository courseRepository, 
                                  EnrollmentRepository enrollmentRepository,
                                  UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public List<Course> cursosPorNivel(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getDifficultyLevel() == level)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public Set<String> instrutoresComCursosAtivos() {
        return courseRepository.findAll().stream()
                .filter(Course::isActive)
                .map(Course::getInstructorName)
                .distinct()
                .collect(Collectors.toSet());
    }

    public Map<SubscriptionPlan, List<Student>> alunosAgrupadosPorPlano() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.groupingBy(Student::getSubscriptionPlan));
    }

    public double mediaGeralProgresso() {
        return enrollmentRepository.findAll().stream()
                .mapToInt(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> alunoComMaisMatriculas() {
        Map<Student, Long> contagem = enrollmentRepository.findAll().stream()
                .filter(e -> e.getCourse().isActive())
                .collect(Collectors.groupingBy(Enrollment::getStudent, Collectors.counting()));

        return contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}