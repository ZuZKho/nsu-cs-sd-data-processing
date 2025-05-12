package ru.nsu.goncharov.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.goncharov.spring.entities.schedule.NewFlight;
import ru.nsu.goncharov.spring.entities.schedule.OldFlight;
import ru.nsu.goncharov.spring.repositories.schedule.NewFlightRepository;
import ru.nsu.goncharov.spring.repositories.schedule.OldFlightRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FlightGenerationService {

    private NewFlightRepository newFlightRepository;
    private NewFlightService newFlightService;
    private OldFlightRepository oldFlightRepository;

    private final Duration SCHEDULE_DELAY = Duration.ofDays(10);
    // Каждый самолет летает в одно время,
    // По одинаковым дням недели.
    // Для каждого самолета будем проверять,
    // Если на 1 месяц вперед нет его полета, добавляем.

    public void updateFlights() {
        List<String> flightNumbers = oldFlightRepository.findAllFlightNumbers();
        flightNumbers.forEach(flightNo -> {
            List<OldFlight> lastFlights = oldFlightRepository.findLast7FlightsByFlightNo(flightNo);

            // Get days of lastFlights
            Set<DayOfWeek> dows = new HashSet<>();
            var flight = lastFlights.getFirst();
            Duration duration = Duration.between(flight.getScheduledDeparture(), flight.getScheduledArrival());

            lastFlights.forEach(lastFlight -> {
                dows.add(lastFlight.getScheduledDeparture().atZone(ZoneId.systemDefault()).getDayOfWeek());
            });

            while(true) {
                List<NewFlight> tmp = newFlightRepository.findLastFlightByFlightNo(flightNo);
                NewFlight lastFlight = tmp.isEmpty() ? null : tmp.getFirst();

                Instant nextDeparture = null;
                if (lastFlight != null) {
                    nextDeparture = lastFlight.getScheduledDeparture().plus(1, ChronoUnit.DAYS);
                } else {
                    nextDeparture = lastFlights.getFirst().getScheduledDeparture();
                    while(nextDeparture.isBefore(Instant.now().minus(1, ChronoUnit.DAYS))) {
                        nextDeparture = nextDeparture.plus(1, ChronoUnit.DAYS);
                    }
                }

                // just iterating by days from now
                while(!dows.contains(nextDeparture.atZone(ZoneId.systemDefault()).getDayOfWeek())) {
                    nextDeparture = nextDeparture.plus(1, ChronoUnit.DAYS);
                }

                if (Duration.between(Instant.now(), nextDeparture).getSeconds() > SCHEDULE_DELAY.getSeconds()) {
                    break;
                }

                // Insert new flight...
                System.out.println("New Flight Created: " + flightNo + ", " + nextDeparture);
                NewFlight testFlight = new NewFlight();
                testFlight.setFlightNo(flightNo);
                testFlight.setDepartureAirport(flight.getDepartureAirport());
                testFlight.setArrivalAirport(flight.getArrivalAirport());
                testFlight.setScheduledDeparture(nextDeparture);
                testFlight.setScheduledArrival(nextDeparture.plus(duration));
                testFlight.setStatus("Scheduled");
                testFlight.setAircraftCode(flight.getAircraftCode());

                newFlightService.saveNewFlight(testFlight);
            }
        });
    }

}
