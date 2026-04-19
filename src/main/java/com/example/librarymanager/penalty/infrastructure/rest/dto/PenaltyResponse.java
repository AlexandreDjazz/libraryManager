package com.example.librarymanager.penalty.infrastructure.rest.dto;

import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyStatus;
import com.example.librarymanager.penalty.domain.PenaltyType;

import java.math.BigDecimal;
import java.time.Instant;

public record PenaltyResponse(
        String id,
        String userId,
        PenaltyType type,
        BigDecimal amount,
        String reason,
        PenaltyStatus status,
        Instant createdAt
) {
    public static PenaltyResponse from(Penalty penalty) {
        return new PenaltyResponse(penalty.getId().value(), penalty.getUserId().value(),
                penalty.getType(), penalty.getAmount(), penalty.getReason(),
                penalty.getStatus(), penalty.getCreatedAt());
    }
}
