package ru.nsu.goncharov.spring.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;

import java.time.*;
import java.util.TimeZone;

@Entity
@Table(name = "flights")
@Data
public class Flight {

    @Id
    @Column(name = "flight_id")
    private Long flightId;
    @Column(name = "flight_no")
    private String flightNo;
    private String aircraft_code;

    @Column(name = "departure_airport")
    private String departureAirport;
    private LocalDateTime departure_date_time;
    private TimeZone departure_timezone;
    private DayOfWeek departureDow;

    @Column(name = "arrival_airport")
    private String arrivalAirport;
    private LocalDateTime arrival_date_time;
    private TimeZone arrival_timezone;
    private DayOfWeek arrivalDow;
}






//    // Getters for transient fields.
//    public ZonedDateTime getArrivalDateTime() {
//        if (arrivalDateTime == null && scheduledArrival != null) {
//            arrivalDateTime = scheduledArrival.atZone(ZoneId.of(arrivalAirport.getTimezone()));
//        }
//        return arrivalDateTime;
//    }
//    public ZonedDateTime getDepartureDateTime() {
//        if (departureDateTime == null && scheduledDeparture != null) {
//            departureDateTime = scheduledDeparture.atZone(ZoneId.of(departureAirport.getTimezone()));
//        }
//        return departureDateTime;
//    }
//    public DayOfWeek getDepartureDow() {
//        return getDepartureDateTime().getDayOfWeek();
//    }
//    public DayOfWeek getArrivalDow() {
//        return getArrivalDateTime().getDayOfWeek();
//    }