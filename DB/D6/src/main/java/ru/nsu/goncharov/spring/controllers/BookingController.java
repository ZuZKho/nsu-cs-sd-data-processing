package ru.nsu.goncharov.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.goncharov.spring.dtos.booking.BookingDto;
import ru.nsu.goncharov.spring.dtos.booking.CreateBookingDto;
import ru.nsu.goncharov.spring.services.FlightBookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDto> createBooking(@RequestBody CreateBookingDto createBookingDto) {
        BookingDto bookingDto = flightBookingService.bookFlight(createBookingDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingDto);
    }
}
