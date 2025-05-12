package ru.nsu.goncharov.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.goncharov.spring.dtos.FlightDto;
import ru.nsu.goncharov.spring.dtos.RouteDto;
import ru.nsu.goncharov.spring.entities.route.Route;
import ru.nsu.goncharov.spring.entities.utils.BookingClass;
import ru.nsu.goncharov.spring.entities.utils.SupportedLanguage;
import ru.nsu.goncharov.spring.mappers.FlightMapper;
import ru.nsu.goncharov.spring.repositories.FlightRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FlightsController {
    private FlightRepository flightRepository;


    /* Example query
        http://localhost:8080/arrivals/KHV?fromTime=18:00&toTime=19:00&flightNumber=PG0651&daysOfWeek=MONDAY,FRIDAY
     */

    @GetMapping("/arrivals/{airport_code}")
    public List<FlightDto> getArrivalsSchedule(
            @PathVariable String airport_code,
            @RequestParam(required = false) Set<DayOfWeek> daysOfWeek,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime fromTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime toTime,
            @RequestParam(required = false) String flightNumber) {

        if (fromTime != null && toTime != null && toTime.isBefore(fromTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toTime have to go after fromTime");
        }

        Set<Integer> internalDaysOfWeek = daysOfWeek == null ?
                new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)) :
                daysOfWeek.stream().map(it -> (it.getValue() + 6) % 7).collect(Collectors.toSet());

        return flightRepository.findArrivalFlights(airport_code, fromTime, toTime, flightNumber, internalDaysOfWeek)
                .stream()
                .map(flight -> FlightMapper.toDto(flight, SupportedLanguage.EN))
                .toList();
    }

    @GetMapping("/departures/{airport_code}")
    public List<FlightDto> getDeparturesSchedule(
            @PathVariable String airport_code,
            @RequestParam(required = false) Set<DayOfWeek> daysOfWeek,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime fromTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime toTime,
            @RequestParam(required = false) String flightNumber) {

        if (fromTime != null && toTime != null && toTime.isBefore(fromTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toTime have to go after fromTime");
        }

        Set<Integer> internalDaysOfWeek = daysOfWeek == null ?
                new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)) :
                daysOfWeek.stream().map(it -> (it.getValue() + 6) % 7).collect(Collectors.toSet());

        return flightRepository.findDepartureFlights(airport_code, fromTime, toTime, flightNumber, internalDaysOfWeek)
                .stream()
                .map(flight -> FlightMapper.toDto(flight, SupportedLanguage.EN))
                .toList();
    }

    /*
        http://localhost:8080/routes?source=OVB&destination=SVO&departureDate=2016-12-11&maxConnections=2 - example
     */

    @GetMapping("/routes")
    public List<RouteDto> getRoutes(
            // Required:
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,

            // Optional:
            @RequestParam(required = false) Integer maxConnections,
            @RequestParam(required = false, defaultValue = "ECONOMY") BookingClass bookingClass
    ) {
        if (maxConnections == null) {
            maxConnections = 10;
        }

        List<Route> routes = flightRepository.findRoutes(departureDate, source, destination, maxConnections);

        return routes.stream()
                .map(route -> FlightMapper.toDto(route))
                .toList();
    }
}