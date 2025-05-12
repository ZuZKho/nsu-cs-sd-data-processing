package ru.nsu.goncharov.spring.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.goncharov.spring.dtos.FlightDto;

import java.util.ArrayList;
import java.util.List;

@Converter
public class RouteConverter implements AttributeConverter<List<FlightDto>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<FlightDto> attribute) {
        // convert to JSON
        return "";
    }

    @Override
    public List<FlightDto> convertToEntityAttribute(String dbData) {
        // parse from JSON
        return new ArrayList<>();
    }
}
