package com.example.librarymanager.penalty.application.usecase;

import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.domain.PenaltyType;
import com.example.librarymanager.user.domain.UserId;

import java.math.BigDecimal;

public interface CreatePenalty {
    record Command(UserId userId, PenaltyType type, BigDecimal amount, String reason) {}
    PenaltyId handle(Command command);
}
