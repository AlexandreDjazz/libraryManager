package com.example.librarymanager.loan.domain;

public record LoanId(String value) {
    public static LoanId of(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("LoanId cannot be blank");
        return new LoanId(value);
    }
}
