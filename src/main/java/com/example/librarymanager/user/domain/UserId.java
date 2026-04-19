package com.example.librarymanager.user.domain;

public record UserId(String value) {
    public static UserId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("UserId cannot be blank");
        return new UserId(value);
    }
}
