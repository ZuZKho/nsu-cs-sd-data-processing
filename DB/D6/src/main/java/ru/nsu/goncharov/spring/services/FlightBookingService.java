package ru.nsu.goncharov.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.goncharov.spring.dtos.ContactInfoDto;
import ru.nsu.goncharov.spring.dtos.booking.BookingException;
import ru.nsu.goncharov.spring.dtos.booking.BookingDto;
import ru.nsu.goncharov.spring.dtos.booking.CreateBookingDto;
import ru.nsu.goncharov.spring.entities.booking.Booking;
import ru.nsu.goncharov.spring.entities.booking.Ticket;
import ru.nsu.goncharov.spring.entities.booking.TicketFlight;
import ru.nsu.goncharov.spring.repositories.FlightRepository;
import ru.nsu.goncharov.spring.repositories.booking.BookingRepository;
import ru.nsu.goncharov.spring.repositories.booking.TicketFlightRepository;
import ru.nsu.goncharov.spring.repositories.booking.TicketRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {})
@AllArgsConstructor
public class FlightBookingService {

    private TicketFlightRepository ticketFlightRepository;
    private BookingRepository bookingRepository;
    private TicketRepository ticketRepository;
    private FlightRepository flightRepository;

    // TODO: перекинуть расписание на new_flights

    public BookingDto bookFlight(CreateBookingDto createBookingDto) {
        String fareConditions = createBookingDto.getFareConditions().toString();
        System.out.println(fareConditions);
        System.out.println(createBookingDto.getFlightIds().toString());

        // Stage 1: gather route prices...
        ArrayList<Double> prices = new ArrayList<>();
        createBookingDto.getFlightIds().forEach((Integer flightId) -> {
            // first of all check places availability
            Optional<String> aircraft = flightRepository.findAircraftCode(flightId);
            if (aircraft.isEmpty()) {
                throw new BookingException("Flight with flightId " + flightId + " does not exist.");
            }

            Optional<Integer> totalSeats = flightRepository.findTotalSeats(aircraft.get(), fareConditions);
            Optional<Integer> usedSeats = flightRepository.findUsedSeatsByFlightId(flightId, fareConditions);
            Optional<Double> price = flightRepository.findPriceByFlightId(flightId, fareConditions);
            if (totalSeats.isEmpty() || usedSeats.isEmpty() || price.isEmpty()) {
                throw new BookingException("Internal error");
            }

            if (totalSeats.get() <= usedSeats.get()) {
                throw new BookingException("There is no more empty seats on flightId " + flightId
                    + "with fare condition " + fareConditions);
            }

            prices.add(price.get());
        });
        Double totalPrice = prices.stream().reduce(0.0, Double::sum);

        // Creating booking
        Booking booking = new Booking();
        booking.setBookDate(Instant.now());
        booking.setTotalAmount(totalPrice);
        String bookRef = bookingRepository.save(booking).getBookRef();

        // Creating tickets
        Ticket ticket = new Ticket();
        ticket.setBookRef(bookRef);
        ticket.setPassengerId(createBookingDto.getPassengerId());
        ticket.setPassengerName("random_passanger_name");
        ticket.setContactData(new ContactInfoDto("+7777777"));
        String ticketNo = ticketRepository.save(ticket).getTicketNo();

        System.out.println(bookRef);
        System.out.println(ticketNo);

        // Creating ticket_flights

        for(var i = 0; i < createBookingDto.getFlightIds().size(); i++) {
            Integer flightId = createBookingDto.getFlightIds().get(i);
            TicketFlight ticketFlight = new TicketFlight();
            ticketFlight.setTicketNo(ticketNo);
            ticketFlight.setFlightId(flightId);
            ticketFlight.setFareConditions(fareConditions);
            ticketFlight.setAmount(prices.get(i));
            ticketFlightRepository.save(ticketFlight);
        };

        return new BookingDto(bookRef, ticketNo, totalPrice);
    }

}
