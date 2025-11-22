package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.domain.entities.SupportTicket;
import java.util.Optional;

public class ProcessarTicketUseCase {
    private final TicketRepository ticketRepository;

    public ProcessarTicketUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public SupportTicket execute() {
        Optional<SupportTicket> ticketOpt = ticketRepository.nextTicket();
        
        if (ticketOpt.isEmpty()) {
            throw new RuntimeException("Não há tickets na fila de espera.");
        }
        
        SupportTicket ticket = ticketOpt.get();
        ticket.closeTicket();
        return ticket;
    }
}