package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.domain.entities.SupportTicket;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class TicketRepositoryImpl implements TicketRepository {

    private final Queue<SupportTicket> queue = new ArrayDeque<>();

    @Override
    public void addTicket(SupportTicket ticket) {
        queue.offer(ticket);
    }

    @Override
    public Optional<SupportTicket> nextTicket() {
        return Optional.ofNullable(queue.poll());
    }
    
    @Override
    public Optional<SupportTicket> peekTicket() {
        return Optional.ofNullable(queue.peek());
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}