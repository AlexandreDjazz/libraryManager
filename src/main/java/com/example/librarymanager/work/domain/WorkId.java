package com.example.librarymanager.work.domain;

public record WorkId(String value) {
    public static WorkId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("WorkId cannot be blank");
        return new WorkId(value);
    }
}
