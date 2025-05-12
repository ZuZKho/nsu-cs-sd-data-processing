package ru.nsu.goncharov.spring.entities.booking;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import ru.nsu.goncharov.spring.dtos.ContactInfoDto;


import java.util.Map;

@Entity
@Data
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(generator = "ticket-id-generator")
    @GenericGenerator(
            name = "ticket-id-generator",
            strategy = "ru.nsu.goncharov.spring.entities.booking.TicketIdGenerator"
    )
    @Column(name = "ticket_no", length = 13)
    private String ticketNo;

    @Column(name = "book_ref")
    private String bookRef;

    @Column(name = "passenger_id")
    private String passengerId;

    @Column(name = "passenger_name")
    private String passengerName;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "contact_data", columnDefinition = "jsonb")
    private ContactInfoDto contactData;
}
