package ru.nsu.goncharov.spring.repositories.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.goncharov.spring.entities.booking.TicketFlight;
import ru.nsu.goncharov.spring.entities.booking.TicketFlightId;

public interface TicketFlightRepository extends JpaRepository<TicketFlight, TicketFlightId> {
}
