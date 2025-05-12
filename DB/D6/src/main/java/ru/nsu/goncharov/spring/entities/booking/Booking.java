package ru.nsu.goncharov.spring.entities.booking;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.processing.Pattern;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(
            name = "custom-id-generator",
            strategy = "ru.nsu.goncharov.spring.entities.booking.BookRefGenerator"
    )
    @Column(name = "book_ref", length = 6)
    private String bookRef;

    @Column(name = "book_date")
    private Instant bookDate;

    @Column(name = "total_amount")
    private Double totalAmount;
}
