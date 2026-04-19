package com.example.librarymanager.copy.domain;

public record CopyId(String value) {
    public static CopyId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("CopyId cannot be blank");
        return new CopyId(value);
    }
}
