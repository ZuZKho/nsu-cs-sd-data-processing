package ru.nsu.goncharov.spring.repositories.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.goncharov.spring.entities.booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
