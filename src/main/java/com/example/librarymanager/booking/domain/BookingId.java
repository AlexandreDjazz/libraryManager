package com.example.librarymanager.booking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record BookingId(
        @Column(name = "id", nullable = false, unique = true, updatable = false)
        String value
) {
}