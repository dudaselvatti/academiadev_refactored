package br.com.academiadev.main;

import br.com.academiadev.application.usecases.AbrirTicketUseCase;
import br.com.academiadev.application.usecases.GerarRelatoriosUseCase;
import br.com.academiadev.application.usecases.ListarCursosUseCase;
import br.com.academiadev.application.usecases.MatricularAlunoUseCase;
import br.com.academiadev.application.usecases.ProcessarTicketUseCase;
import br.com.academiadev.infrastructure.persistence.CourseRepositoryImpl;
import br.com.academiadev.infrastructure.persistence.EnrollmentRepositoryImpl;
import br.com.academiadev.infrastructure.persistence.TicketRepositoryImpl;
import br.com.academiadev.infrastructure.persistence.UserRepositoryImpl;
import br.com.academiadev.infrastructure.ui.ConsoleController;

public class Main {
    public static void main(String[] args) {

        var userRepo = new UserRepositoryImpl();
        var courseRepo = new CourseRepositoryImpl();
        var enrollmentRepo = new EnrollmentRepositoryImpl();
        var ticketRepo = new TicketRepositoryImpl();



        InitialData.populate(userRepo, courseRepo);

        var matricularUseCase = new MatricularAlunoUseCase(userRepo, courseRepo, enrollmentRepo);
        var listarCursosUseCase = new ListarCursosUseCase(courseRepo);
        var abrirTicketUseCase = new AbrirTicketUseCase(ticketRepo, userRepo);
        var processarTicketUseCase = new ProcessarTicketUseCase(ticketRepo);
        var relatoriosUseCase = new GerarRelatoriosUseCase(courseRepo, enrollmentRepo, userRepo);

        var gerenciarCursoUseCase = new br.com.academiadev.application.usecases.GerenciarCursoUseCase(courseRepo);
        var gerenciarPlanoUseCase = new br.com.academiadev.application.usecases.GerenciarPlanoAlunoUseCase(userRepo);
        var atualizarProgressoUseCase = new br.com.academiadev.application.usecases.AtualizarProgressoUseCase(enrollmentRepo);
        var cancelarMatriculaUseCase = new br.com.academiadev.application.usecases.CancelarMatriculaUseCase(enrollmentRepo);

        var controller = new ConsoleController(
                listarCursosUseCase,
                matricularUseCase,
                abrirTicketUseCase,
                processarTicketUseCase,
                relatoriosUseCase,
                gerenciarCursoUseCase,      
                gerenciarPlanoUseCase,     
                atualizarProgressoUseCase,
                cancelarMatriculaUseCase,  
                userRepo,
                enrollmentRepo
        );

        controller.start();
    }
}