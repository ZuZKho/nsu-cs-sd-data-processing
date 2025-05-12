package ru.nsu.goncharov.spring.dtos;

public record AirportDto(
        String code,
        String name,
        String city,
        String timezone
) {}