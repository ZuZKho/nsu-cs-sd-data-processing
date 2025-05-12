package ru.nsu.goncharov.spring.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingDto {
    String bookRef;
    String ticketNo;
    Double totalPrice;
}
