package ru.nsu.goncharov.spring.entities.schedule;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "flights")
@Data
public class OldFlight {

    @Id
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "flight_no", columnDefinition = "character(6)", length = 6)
    private String flightNo;

    @Column(name = "arrival_airport", columnDefinition = "char(3)", length = 3)
    private String arrivalAirport;

    @Column(name = "departure_airport", columnDefinition = "char(3)", length = 3)
    private String departureAirport;

    @Column(name = "scheduled_departure", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant scheduledDeparture;

    @Column(name = "scheduled_arrival", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant scheduledArrival;

    @Column(name = "status")
    private String status;

    @Column(name = "aircraft_code", columnDefinition = "char(3)", length = 3)
    private String aircraftCode;
}
