package ru.nsu.goncharov.spring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.postgresql.geometric.PGpoint;
import ru.nsu.goncharov.spring.entities.utils.Language;
import ru.nsu.goncharov.spring.entities.utils.PointConverter;
import java.awt.Point;

@Entity
@Data
@Table(name = "airports_data")
public class Airport {

    @Id
    @Column(name = "airport_code")
    private String code;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "airport_name", columnDefinition = "jsonb")
    private Language name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Embedded
    private Language city;

    private String coordinates;
    private String timezone;
}

