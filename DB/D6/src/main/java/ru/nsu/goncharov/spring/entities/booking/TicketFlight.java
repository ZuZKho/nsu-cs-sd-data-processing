package ru.nsu.goncharov.spring.entities.booking;

import jakarta.persistence.*;
import lombok.Data;
import ru.nsu.goncharov.spring.dtos.booking.FareConditionDto;

@Entity
@Data
@Table(name = "ticket_flights")
@IdClass(TicketFlightId.class)
public class TicketFlight {

    @Id
    @Column(name = "ticket_no")
    private String ticketNo;

    @Id
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "fare_conditions")
    private String fareConditions;

    private Double amount;
}
