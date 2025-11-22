package br.com.academiadev.main;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;

public class InitialData {

    public static void populate(UserRepository userRepo, CourseRepository courseRepo) {

        courseRepo.save(new Course("Java Clean Architecture", "Aprenda arquitetura limpa", "Otávio Lemos", 10, DifficultyLevel.ADVANCED));
        courseRepo.save(new Course("Spring Boot Básico", "Introdução ao Spring", "Michelli Brito", 8, DifficultyLevel.BEGINNER));
        courseRepo.save(new Course("Python para Dados", "Data Science com Python", "Guanabara", 12, DifficultyLevel.INTERMEDIATE));

        userRepo.save(new Student("Maria Eduarda", "maria@ifsp.edu.br", SubscriptionPlan.BASIC));

        userRepo.save(new Student("João da Silva", "joao@email.com", SubscriptionPlan.PREMIUM));

        userRepo.save(new Admin("Admin Chefe", "admin@academiadev.com"));

        System.out.println("--- Dados Iniciais Carregados com Sucesso ---");
    }
}