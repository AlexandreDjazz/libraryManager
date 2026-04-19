package com.example.librarymanager.loan.infrastructure.rest.dto;

import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanStatus;

import java.time.Instant;

public record LoanResponse(
        String id,
        String copyId,
        String userId,
        Instant startAt,
        Instant dueAt,
        Instant returnedAt,
        int renewCount,
        int maxRenewals,
        LoanStatus status
) {
    public static LoanResponse from(Loan loan) {
        return new LoanResponse(loan.getId().value(), loan.getCopyId().value(),
                loan.getUserId().value(), loan.getStartAt(), loan.getDueAt(),
                loan.getReturnedAt(), loan.getRenewCount(), loan.getMaxRenewals(), loan.getStatus());
    }
}
