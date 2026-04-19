package com.example.librarymanager.penalty.infrastructure.persistence;

import com.example.librarymanager.penalty.domain.*;
import com.example.librarymanager.user.domain.UserId;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "penalty")
class JpaPenalty {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PenaltyType type;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PenaltyStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected JpaPenalty() {}

    static JpaPenalty fromDomain(Penalty penalty) {
        JpaPenalty e = new JpaPenalty();
        e.id = penalty.getId().value();
        e.userId = penalty.getUserId().value();
        e.type = penalty.getType();
        e.amount = penalty.getAmount();
        e.reason = penalty.getReason();
        e.status = penalty.getStatus();
        e.createdAt = penalty.getCreatedAt();
        return e;
    }

    Penalty toDomain() {
        return new Penalty(PenaltyId.of(id), UserId.of(userId),
                type, amount, reason, status, createdAt);
    }
}
