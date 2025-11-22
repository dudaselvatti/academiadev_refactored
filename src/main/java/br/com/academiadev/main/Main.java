package br.com.academiadev.main;

import br.com.academiadev.application.usecases.MatricularAlunoUseCase;
import br.com.academiadev.infrastructure.persistence.CourseRepositoryImpl;
import br.com.academiadev.infrastructure.persistence.EnrollmentRepositoryImpl;
import br.com.academiadev.infrastructure.persistence.UserRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando AcademiaDev...");

        var userRepository = new UserRepositoryImpl();
        var courseRepository = new CourseRepositoryImpl();
        var enrollmentRepository = new EnrollmentRepositoryImpl();

        InitialData.populate(userRepository, courseRepository);

        var matricularAluno = new MatricularAlunoUseCase(userRepository, courseRepository, enrollmentRepository);

        try {
            System.out.println("\n>>> Teste 1: Matriculando Maria no curso de Java...");
            matricularAluno.execute("maria@ifsp.edu.br", "Java Clean Architecture");

            System.out.println("\n>>> Teste 2: Tentando matricular Maria no mesmo curso de novo (Deve falhar)...");
            matricularAluno.execute("maria@ifsp.edu.br", "Java Clean Architecture");

        } catch (Exception e) {
            System.out.println("ERRO ESPERADO: " + e.getMessage());
        }
        
        System.out.println("\n>>> Sistema Finalizado.");
    }
}