package br.com.academiadev.infrastructure.ui;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ConsoleView {

    public void showWelcome() {
        System.out.println("\n==============================");
        System.out.println("      ACADEMIADEV SYSTEM      ");
        System.out.println("==============================");
    }

    public void showLoginMenu() {
        System.out.println("\n1. Entrar (Login por Email)");
        System.out.println("2. Listar Cursos Disponíveis");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public void showAdminMenu(String adminName) {
        System.out.println("\n--- Painel Admin: " + adminName + " ---");
        System.out.println("1. Listar Cursos");
        System.out.println("2. Exportar CSV de Cursos");
        System.out.println("0. Deslogar");
        System.out.print("Opção: ");
    }

    public void showStudentMenu(String studentName) {
        System.out.println("\n--- Painel Aluno: " + studentName + " ---");
        System.out.println("1. Listar Cursos Disponíveis");
        System.out.println("2. Minhas Matrículas");
        System.out.println("3. Matricular em um Curso");
        System.out.println("0. Deslogar");
        System.out.print("Opção: ");
    }

    public void showCourses(List<Course> courses) {
        System.out.println("\n--- Catálogo de Cursos ---");
        if (courses.isEmpty()) System.out.println("Nenhum curso disponível.");
        for (Course c : courses) {
            System.out.println("- " + c.getTitle() + " [" + c.getStatus() + "] (" + c.getDifficultyLevel() + ")");
        }
    }

    public void showEnrollments(List<Enrollment> enrollments) {
        System.out.println("\n--- Minhas Matrículas ---");
        if (enrollments.isEmpty()) System.out.println("Você não está matriculado em nenhum curso.");
        for (Enrollment e : enrollments) {
            System.out.println("- " + e.getCourse().getTitle() + " | Progresso: " + e.getProgress() + "%");
        }
    }

    // ... métodos anteriores ...

    public void showReportsMenu() {
        System.out.println("\n--- MENU DE RELATÓRIOS (ADMIN) ---");
        System.out.println("1. Cursos por Nível (Avançado)");
        System.out.println("2. Instrutores Ativos");
        System.out.println("3. Alunos por Plano");
        System.out.println("4. Estatísticas de Progresso");
        System.out.println("0. Voltar");
        System.out.print("Opção: ");
    }

    public void showInstructors(Set<String> instructors) {
        System.out.println("\n=== Instrutores Ativos ===");
        instructors.forEach(name -> System.out.println("- " + name));
    }

    public void showStudentsByPlan(Map<SubscriptionPlan, List<Student>> map) {
        System.out.println("\n=== Alunos por Plano ===");
        map.forEach((plan, students) -> {
            System.out.println("[" + plan + "]");
            students.forEach(s -> System.out.println("  - " + s.getName()));
        });
    }

    public void showStatistics(double media, Optional<Student> topStudent) {
        System.out.println("\n=== Estatísticas Gerais ===");
        System.out.printf("Média de Progresso Global: %.2f%%\n", media);
        
        if (topStudent.isPresent()) {
            System.out.println("Aluno mais estudioso (Mais matrículas): " + topStudent.get().getName());
        } else {
            System.out.println("Aluno mais estudioso: Nenhum dado suficiente.");
        }
    }

    public void showMessage(String message) {
        System.out.println(">> " + message);
    }

    public void showError(String error) {
        System.out.println("ERRO: " + error);
    }
}