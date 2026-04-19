package com.example.librarymanager.hold.domain;

import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;

import java.time.Instant;

public class Hold {
    private final HoldId id;
    private final WorkId workId;
    private final UserId userId;
    private HoldStatus status;
    private int queuePosition;
    private Instant pickupUntil;
    private final Instant createdAt;

    public Hold(HoldId id, WorkId workId, UserId userId, HoldStatus status,
                int queuePosition, Instant pickupUntil, Instant createdAt) {
        this.id = id;
        this.workId = workId;
        this.userId = userId;
        this.status = status;
        this.queuePosition = queuePosition;
        this.pickupUntil = pickupUntil;
        this.createdAt = createdAt;
    }

    public void markReadyForPickup(Instant pickupUntil) {
        this.status = HoldStatus.READY_FOR_PICKUP;
        this.pickupUntil = pickupUntil;
        this.queuePosition = 0;
    }

    public void cancel() { this.status = HoldStatus.CANCELLED; }
    public void expire() { this.status = HoldStatus.EXPIRED; }
    public void pickedUp() { this.status = HoldStatus.PICKED_UP; }
    public void decrementPosition() { if (queuePosition > 0) queuePosition--; }

    public boolean isExpired(Instant now) {
        return status == HoldStatus.READY_FOR_PICKUP && pickupUntil != null && now.isAfter(pickupUntil);
    }

    public HoldId getId() { return id; }
    public WorkId getWorkId() { return workId; }
    public UserId getUserId() { return userId; }
    public HoldStatus getStatus() { return status; }
    public int getQueuePosition() { return queuePosition; }
    public Instant getPickupUntil() { return pickupUntil; }
    public Instant getCreatedAt() { return createdAt; }
}
