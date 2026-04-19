package com.example.librarymanager.hold.domain;

public record HoldId(String value) {
    public static HoldId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("HoldId cannot be blank");
        return new HoldId(value);
    }
}
