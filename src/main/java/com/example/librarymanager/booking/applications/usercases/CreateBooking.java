package com.example.librarymanager.booking.applications.usercases;

import com.example.librarymanager.booking.applications.models.CreateBookingRequest;
import com.example.librarymanager.booking.domain.BookingId;

public interface CreateBooking {
    BookingId handle(CreateBookingRequest createBookingRequest);
}
