package ru.nsu.goncharov.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.goncharov.spring.dtos.AirportDto;
import ru.nsu.goncharov.spring.dtos.CityDto;
import ru.nsu.goncharov.spring.entities.Airport;
import ru.nsu.goncharov.spring.entities.City;
import ru.nsu.goncharov.spring.entities.utils.SupportedLanguage;
import ru.nsu.goncharov.spring.mappers.AirportMapper;
import ru.nsu.goncharov.spring.repositories.AirportRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class AirportController {
    private AirportRepository airportRepository;

    @GetMapping("/airports")
    public List<AirportDto> getAirports(
            @RequestParam(required = false, defaultValue = "EN")
            SupportedLanguage lang
    ) {
        return airportRepository.findAll()
                .stream()
                .map((Airport airport) -> AirportMapper.toDto(airport, lang))
                .toList();
    }

    @GetMapping("/airports/{city}")
    public ResponseEntity<List<AirportDto>> getAirportsByCity(
            @PathVariable String city,
            @RequestParam(required = false, defaultValue = "EN") SupportedLanguage lang
    ) {
        List<Airport> airports = airportRepository.findByCityInAnyLanguage(city);

        if (airports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<AirportDto> dtos = airports
                .stream()
                .map((Airport airport) -> AirportMapper.toDto(airport, lang))
                .toList();

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/cities")
    public List<CityDto> getCities(
            @RequestParam(required = false, defaultValue = "EN")
            SupportedLanguage lang
    ) {
        return airportRepository.findDistinctCities()
                .stream()
                .map((City city) -> AirportMapper.toDto(city, lang))
                .toList();
    }
}
