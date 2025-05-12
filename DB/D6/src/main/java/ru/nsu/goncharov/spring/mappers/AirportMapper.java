package ru.nsu.goncharov.spring.mappers;

import org.springframework.stereotype.Component;
import ru.nsu.goncharov.spring.dtos.AirportDto;
import ru.nsu.goncharov.spring.dtos.CityDto;
import ru.nsu.goncharov.spring.entities.Airport;
import ru.nsu.goncharov.spring.entities.City;
import ru.nsu.goncharov.spring.entities.utils.Language;
import ru.nsu.goncharov.spring.entities.utils.SupportedLanguage;

public class AirportMapper {

    public static AirportDto toDto(Airport airport, SupportedLanguage language) {
        return new AirportDto(
                airport.getCode(),
                getLocalizedString(airport.getName(), language),
                getLocalizedString(airport.getCity(), language),
                airport.getTimezone()
        );
    }

    public static CityDto toDto(City city, SupportedLanguage language) {
        return new CityDto(
                getLocalizedString(city.getName(), language),
                city.getTimezone()
        );
    }

    private static String getLocalizedString(Language source, SupportedLanguage lang) {
        return lang == SupportedLanguage.RU ? source.getRu() : source.getEn();
    }
}