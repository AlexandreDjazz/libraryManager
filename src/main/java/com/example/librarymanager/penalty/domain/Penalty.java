package com.example.librarymanager.penalty.domain;

import com.example.librarymanager.user.domain.UserId;

import java.math.BigDecimal;
import java.time.Instant;

public class Penalty {
    private final PenaltyId id;
    private final UserId userId;
    private final PenaltyType type;
    private final BigDecimal amount;
    private final String reason;
    private PenaltyStatus status;
    private final Instant createdAt;

    public Penalty(PenaltyId id, UserId userId, PenaltyType type,
                   BigDecimal amount, String reason, PenaltyStatus status, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void resolve() { this.status = PenaltyStatus.RESOLVED; }

    public PenaltyId getId() { return id; }
    public UserId getUserId() { return userId; }
    public PenaltyType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getReason() { return reason; }
    public PenaltyStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
