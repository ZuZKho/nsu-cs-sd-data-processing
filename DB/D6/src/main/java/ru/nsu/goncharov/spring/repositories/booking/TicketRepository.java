package ru.nsu.goncharov.spring.repositories.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.goncharov.spring.entities.booking.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
