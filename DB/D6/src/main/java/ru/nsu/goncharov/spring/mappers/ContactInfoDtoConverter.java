package ru.nsu.goncharov.spring.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.goncharov.spring.dtos.ContactInfoDto;

@Converter
public class ContactInfoDtoConverter implements AttributeConverter<ContactInfoDto, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ContactInfoDto address) {
        try {
            return objectMapper.writeValueAsString(address);
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    @Override
    public ContactInfoDto convertToEntityAttribute(String value) {
        if (value == null) return null;
        try {
            return objectMapper.readValue(value, ContactInfoDto.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}