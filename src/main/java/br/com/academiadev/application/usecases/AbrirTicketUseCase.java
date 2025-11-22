package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.SupportTicket;
import br.com.academiadev.domain.entities.User;
import br.com.academiadev.domain.exceptions.DomainException;

public class AbrirTicketUseCase {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public AbrirTicketUseCase(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void execute(String userEmail, String title, String message) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DomainException("Usuário não encontrado: " + userEmail));

        SupportTicket ticket = new SupportTicket(user, title, message);
        ticketRepository.addTicket(ticket);
    }
}