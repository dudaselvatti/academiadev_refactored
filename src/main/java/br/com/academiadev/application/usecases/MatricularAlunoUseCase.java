package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;
import br.com.academiadev.domain.exceptions.EnrollmentException;

public class MatricularAlunoUseCase {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public MatricularAlunoUseCase(UserRepository userRepository, 
                                  CourseRepository courseRepository, 
                                  EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String studentEmail, String courseTitle) {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new EnrollmentException("Usuário não encontrado: " + studentEmail));

        if (!(user instanceof Student)) {
            throw new EnrollmentException("Apenas alunos podem se matricular em cursos.");
        }
        Student student = (Student) user;

        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new EnrollmentException("Curso não encontrado: " + courseTitle));

        if (!course.isActive()) {
            throw new EnrollmentException("O curso '" + courseTitle + "' não está ativo para novas matrículas.");
        }
        if (enrollmentRepository.existsByStudentAndCourse(studentEmail, courseTitle)) {
            throw new EnrollmentException("Aluno já está matriculado neste curso.");
        }

        student.getEnrollments().clear();
        student.getEnrollments().addAll(enrollmentRepository.findAllByStudentEmail(studentEmail));

        if (!student.canEnroll()) {
            throw new EnrollmentException("Plano Básico atingiu o limite de 3 cursos ativos.");
        }

        Enrollment newEnrollment = new Enrollment(student, course);
        enrollmentRepository.save(newEnrollment);

        student.addEnrollment(newEnrollment);

        System.out.println("SUCESSO: Aluno " + student.getName() + " matriculado em " + course.getTitle());
    }
}