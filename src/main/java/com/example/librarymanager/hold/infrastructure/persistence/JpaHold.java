package com.example.librarymanager.hold.infrastructure.persistence;

import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "hold")
class JpaHold {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HoldStatus status;

    @Column(name = "queue_position", nullable = false)
    private int queuePosition;

    @Column(name = "pickup_until")
    private Instant pickupUntil;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected JpaHold() {}

    static JpaHold fromDomain(Hold hold) {
        JpaHold e = new JpaHold();
        e.id = hold.getId().value();
        e.workId = hold.getWorkId().value();
        e.userId = hold.getUserId().value();
        e.status = hold.getStatus();
        e.queuePosition = hold.getQueuePosition();
        e.pickupUntil = hold.getPickupUntil();
        e.createdAt = hold.getCreatedAt();
        return e;
    }

    Hold toDomain() {
        return new Hold(HoldId.of(id), WorkId.of(workId), UserId.of(userId),
                status, queuePosition, pickupUntil, createdAt);
    }
}
