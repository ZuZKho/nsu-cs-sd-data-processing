package ru.nsu.goncharov.spring.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.goncharov.spring.dtos.FlightDto;
import ru.nsu.goncharov.spring.dtos.RouteDto;
import ru.nsu.goncharov.spring.entities.Flight;
import ru.nsu.goncharov.spring.entities.route.Route;
import ru.nsu.goncharov.spring.entities.utils.Language;
import ru.nsu.goncharov.spring.entities.utils.SupportedLanguage;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static FlightDto toDto(Flight flight, SupportedLanguage lang) {
        return new FlightDto(
                flight.getFlightId().toString(),
                flight.getFlightNo(),
                flight.getAircraft_code(),

                flight.getDepartureDow() + ", " + flight.getDeparture_date_time().format(formatter) + " " + flight.getDeparture_timezone().toZoneId().getRules().getStandardOffset(Instant.now()).getId(),
                flight.getDepartureAirport(),

                flight.getArrivalDow() + ", " + flight.getArrival_date_time().format(formatter) + " " + flight.getArrival_timezone().toZoneId().getRules().getStandardOffset(Instant.now()).getId(),
                flight.getArrivalAirport(),
                "No info"
        );
    }

    public static RouteDto toDto(Route route) {
        List<FlightDto> flights = null;
        try {
            flights = objectMapper.readValue(route.getRouteJson(), new TypeReference<List<FlightDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to List<FlightDto>", e);
        }
        return new RouteDto(flights.size() - 1, flights);
    }

    private static String getLocalizedString(Language source, SupportedLanguage lang) {
        return lang == SupportedLanguage.RU ? source.getRu() : source.getEn();
    }
}

/*
SELECT (scheduled_departure AT TIME ZONE 'Asia/Novosibirsk') AS dep
    FROM flights f
    JOIN airports a ON f.departure_airport = a.airport_code
    LIMIT 12;
 */