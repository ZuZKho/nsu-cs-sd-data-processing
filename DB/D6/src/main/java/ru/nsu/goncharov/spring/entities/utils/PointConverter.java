package ru.nsu.goncharov.spring.entities.utils;
import jakarta.persistence.*;
import org.postgresql.geometric.PGpoint;

@Converter
public class PointConverter implements AttributeConverter<PGpoint, String> {
    @Override
    public String convertToDatabaseColumn(PGpoint attribute) {
        return attribute != null ? "(" + attribute.x + "," + attribute.y + ")" : null;
    }

    @Override
    public PGpoint convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String[] parts = dbData.replace("(", "").replace(")", "").split(",");
        return new PGpoint(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1])
        );
    }
}