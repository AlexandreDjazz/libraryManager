package com.example.librarymanager.penalty.domain;

public record PenaltyId(String value) {
    public static PenaltyId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("PenaltyId cannot be blank");
        return new PenaltyId(value);
    }
}
