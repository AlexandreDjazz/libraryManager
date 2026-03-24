package com.example.librarymanager.booking.applications.services;

import com.example.librarymanager.booking.applications.models.CreateBookingRequest;
import com.example.librarymanager.booking.applications.usercases.CreateBooking;
import com.example.librarymanager.booking.domain.BookingId;
import org.springframework.stereotype.Service;

@Service
public class CreateBookingHandler implements CreateBooking {
    @Override
    public BookingId handle(CreateBookingRequest createBookingRequest) {
        return null;
    }
}
