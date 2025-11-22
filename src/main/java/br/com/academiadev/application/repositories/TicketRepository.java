package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.SupportTicket;
import java.util.Optional;

public interface TicketRepository {
    void addTicket(SupportTicket ticket);
    Optional<SupportTicket> nextTicket();
    Optional<SupportTicket> peekTicket();
    boolean isEmpty();
}