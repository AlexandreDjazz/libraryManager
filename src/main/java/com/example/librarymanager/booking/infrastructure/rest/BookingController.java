package com.example.librarymanager.booking.infrastructure.rest;

import com.example.librarymanager.booking.applications.models.CreateBookingRequest;
import com.example.librarymanager.booking.applications.usercases.CreateBooking;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final CreateBooking createBooking;


    BookingController(CreateBooking createBooking) {
        this.createBooking = createBooking;
    }

    @PostMapping
    ResponseEntity<@NonNull Void> createBooking(@RequestBody CreateBookingRequest request) {
        final var bookingId = createBooking.handle(request);
        return ResponseEntity.created(URI.create("/api/booking/" + bookingId.value())).build();
    }
}
