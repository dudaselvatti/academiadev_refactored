package br.com.academiadev.infrastructure.ui;

import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.application.usecases.*; // Importa TODOS os UseCases
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.infrastructure.utils.GenericCsvExporter;

import java.util.Scanner;

public class ConsoleController {

    private final ConsoleView view;
    private final Scanner scanner;

    // --- DEPENDÊNCIAS (USE CASES) ---
    private final ListarCursosUseCase listarCursosUseCase;
    private final MatricularAlunoUseCase matricularAlunoUseCase;
    private final AbrirTicketUseCase abrirTicketUseCase;
    private final ProcessarTicketUseCase processarTicketUseCase;
    private final GerarRelatoriosUseCase relatoriosUseCase;
    
    // Novos UseCases de Gestão
    private final GerenciarCursoUseCase gerenciarCursoUseCase;
    private final GerenciarPlanoAlunoUseCase gerenciarPlanoAlunoUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final CancelarMatriculaUseCase cancelarMatriculaUseCase;

    // Repositórios (necessários para Login e listagens simples)
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    private User loggedUser = null;

    // CONSTRUTOR GIGANTE (Injeção de Dependência Manual)
    public ConsoleController(ListarCursosUseCase listarCursosUseCase,
                             MatricularAlunoUseCase matricularAlunoUseCase,
                             AbrirTicketUseCase abrirTicketUseCase,
                             ProcessarTicketUseCase processarTicketUseCase,
                             GerarRelatoriosUseCase relatoriosUseCase,
                             GerenciarCursoUseCase gerenciarCursoUseCase,
                             GerenciarPlanoAlunoUseCase gerenciarPlanoAlunoUseCase,
                             AtualizarProgressoUseCase atualizarProgressoUseCase,
                             CancelarMatriculaUseCase cancelarMatriculaUseCase,
                             UserRepository userRepository,
                             EnrollmentRepository enrollmentRepository) {
        
        this.view = new ConsoleView();
        this.scanner = new Scanner(System.in);
        
        this.listarCursosUseCase = listarCursosUseCase;
        this.matricularAlunoUseCase = matricularAlunoUseCase;
        this.abrirTicketUseCase = abrirTicketUseCase;
        this.processarTicketUseCase = processarTicketUseCase;
        this.relatoriosUseCase = relatoriosUseCase;
        this.gerenciarCursoUseCase = gerenciarCursoUseCase;
        this.gerenciarPlanoAlunoUseCase = gerenciarPlanoAlunoUseCase;
        this.atualizarProgressoUseCase = atualizarProgressoUseCase;
        this.cancelarMatriculaUseCase = cancelarMatriculaUseCase;
        
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void start() {
        view.showWelcome();
        boolean running = true;

        while (running) {
            if (loggedUser == null) {
                running = handlePublicMenu();
            } else if (loggedUser instanceof Admin) {
                handleAdminMenu();
            } else if (loggedUser instanceof Student) {
                handleStudentMenu();
            }
        }
        System.out.println("Encerrando sistema...");
    }

    // --- MENUS ---

    private boolean handlePublicMenu() {
        view.showLoginMenu();
        String op = scanner.nextLine();

        switch (op) {
            case "1":
                performLogin();
                break;
            case "2":
                view.showCourses(listarCursosUseCase.execute());
                break;
            case "0":
                return false; // Sair do Loop
            default:
                view.showError("Opção inválida.");
        }
        return true;
    }

    private void handleAdminMenu() {
        // Atualize o texto do menu na ConsoleView para refletir essas opções!
        System.out.println("\n--- MENU ADMIN ---");
        System.out.println("1. Listar Cursos");
        System.out.println("2. Exportar CSV");
        System.out.println("3. Atender Ticket Suporte");
        System.out.println("4. Relatórios Avançados");
        System.out.println("5. Ativar/Inativar Curso");
        System.out.println("6. Alterar Plano de Aluno");
        System.out.println("0. Logout");
        System.out.print("Opção: ");
        
        String op = scanner.nextLine();

        switch (op) {
            case "1":
                view.showCourses(listarCursosUseCase.execute());
                break;
            case "2":
                String csv = GenericCsvExporter.export(listarCursosUseCase.execute(), 
                        new String[]{"title", "instructorName", "status", "difficultyLevel"});
                view.showMessage("CSV Gerado:\n" + csv);
                break;
            case "3":
                try {
                    var ticket = processarTicketUseCase.execute();
                    view.showMessage("Ticket Processado: [" + ticket.getTitle() + "] - " + ticket.getMessage());
                } catch (Exception e) {
                    view.showError(e.getMessage());
                }
                break;
            case "4":
                handleReportsMenu();
                break;
            case "5": // Gerenciar Status Curso
                System.out.print("Digite o nome exato do curso: ");
                String cursoStatus = scanner.nextLine();
                try {
                    gerenciarCursoUseCase.toggleStatus(cursoStatus);
                    view.showMessage("Status do curso alterado!");
                } catch (Exception e) { view.showError(e.getMessage()); }
                break;
            case "6": // Mudar Plano
                System.out.print("Email do Aluno: ");
                String emailPlano = scanner.nextLine();
                System.out.print("Novo Plano (BASIC/PREMIUM): ");
                String planoStr = scanner.nextLine().toUpperCase();
                try {
                    SubscriptionPlan novoPlano = SubscriptionPlan.valueOf(planoStr);
                    gerenciarPlanoAlunoUseCase.execute(emailPlano, novoPlano);
                    view.showMessage("Plano atualizado com sucesso.");
                } catch (Exception e) { view.showError("Erro: " + e.getMessage()); }
                break;
            case "0":
                loggedUser = null;
                break;
            default:
                view.showError("Opção inválida.");
        }
    }

    private void handleStudentMenu() {
        // Atualize o texto do menu na ConsoleView para refletir essas opções!
        System.out.println("\n--- MENU ALUNO ---");
        System.out.println("1. Listar Cursos Disponíveis");
        System.out.println("2. Minhas Matrículas");
        System.out.println("3. Nova Matrícula");
        System.out.println("4. Abrir Ticket Suporte");
        System.out.println("5. Atualizar Progresso");
        System.out.println("6. Cancelar Matrícula");
        System.out.println("0. Logout");
        System.out.print("Opção: ");

        String op = scanner.nextLine();

        switch (op) {
            case "1":
                view.showCourses(listarCursosUseCase.execute());
                break;
            case "2":
                var myEnrollments = enrollmentRepository.findAllByStudentEmail(loggedUser.getEmail());
                view.showEnrollments(myEnrollments);
                break;
            case "3":
                System.out.print("Nome do Curso para Matricular: ");
                String cursoMatricula = scanner.nextLine();
                try {
                    matricularAlunoUseCase.execute(loggedUser.getEmail(), cursoMatricula);
                    view.showMessage("Matrícula realizada!");
                } catch (Exception e) { view.showError(e.getMessage()); }
                break;
            case "4":
                System.out.print("Assunto: ");
                String assunto = scanner.nextLine();
                System.out.print("Mensagem: ");
                String msg = scanner.nextLine();
                abrirTicketUseCase.execute(loggedUser.getEmail(), assunto, msg);
                view.showMessage("Ticket enviado.");
                break;
            case "5":
                System.out.print("Nome do Curso: ");
                String cursoProg = scanner.nextLine();
                System.out.print("Novo Progresso (0-100): ");
                try {
                    int prog = Integer.parseInt(scanner.nextLine());
                    atualizarProgressoUseCase.execute(loggedUser.getEmail(), cursoProg, prog);
                    view.showMessage("Progresso salvo!");
                } catch (Exception e) { view.showError(e.getMessage()); }
                break;
            case "6":
                System.out.print("Nome do Curso para Cancelar: ");
                String cursoCancel = scanner.nextLine();
                try {
                    cancelarMatriculaUseCase.execute(loggedUser.getEmail(), cursoCancel);
                    view.showMessage("Matrícula cancelada.");
                } catch (Exception e) { view.showError(e.getMessage()); }
                break;
            case "0":
                loggedUser = null;
                break;
            default:
                view.showError("Opção inválida.");
        }
    }

    private void handleReportsMenu() {
        view.showReportsMenu();
        String op = scanner.nextLine();
        switch (op) {
            case "1":
                var cursosAdv = relatoriosUseCase.cursosPorNivel(DifficultyLevel.ADVANCED);
                view.showCourses(cursosAdv);
                break;
            case "2":
                view.showInstructors(relatoriosUseCase.instrutoresComCursosAtivos());
                break;
            case "3":
                view.showStudentsByPlan(relatoriosUseCase.alunosAgrupadosPorPlano());
                break;
            case "4":
                view.showStatistics(relatoriosUseCase.mediaGeralProgresso(), relatoriosUseCase.alunoComMaisMatriculas());
                break;
            case "0":
                break;
            default:
                view.showError("Opção inválida");
        }
    }

    private void performLogin() {
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();
        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            loggedUser = userOpt.get();
            view.showMessage("Bem-vindo(a), " + loggedUser.getName() + "!");
        } else {
            view.showError("Usuário não encontrado.");
        }
    }
}