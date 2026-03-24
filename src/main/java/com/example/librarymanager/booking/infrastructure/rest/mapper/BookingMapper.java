package com.example.librarymanager.booking.infrastructure.rest.mapper;

import com.example.librarymanager.booking.domain.Booking;
import com.example.librarymanager.booking.infrastructure.rest.dto.BookingDTO;

public class BookingMapper {

    public BookingDTO toBookingDTO(Booking booking) {
        return new BookingDTO(
                booking.getId().value()
        );
    }
}
