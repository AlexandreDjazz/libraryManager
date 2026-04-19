package com.example.librarymanager.loan.domain;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.user.domain.UserId;

import java.time.Instant;

public class Loan {
    private final LoanId id;
    private final CopyId copyId;
    private final UserId userId;
    private final Instant startAt;
    private Instant dueAt;
    private Instant returnedAt;
    private int renewCount;
    private LoanStatus status;
    private final int maxRenewals;

    public Loan(LoanId id, CopyId copyId, UserId userId, Instant startAt,
                Instant dueAt, Instant returnedAt, int renewCount,
                LoanStatus status, int maxRenewals) {
        this.id = id;
        this.copyId = copyId;
        this.userId = userId;
        this.startAt = startAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
        this.renewCount = renewCount;
        this.status = status;
        this.maxRenewals = maxRenewals;
    }

    public boolean canRenew(boolean hasPendingHold, boolean hasBlockingPenalty) {
        return status == LoanStatus.ACTIVE
                && renewCount < maxRenewals
                && !hasPendingHold
                && !hasBlockingPenalty;
    }

    public void renew(Instant newDueAt) {
        this.dueAt = newDueAt;
        this.renewCount++;
    }

    public void markReturned(Instant returnedAt) {
        this.returnedAt = returnedAt;
        this.status = LoanStatus.RETURNED;
    }

    public boolean isOverdue(Instant now) {
        return status == LoanStatus.ACTIVE && now.isAfter(dueAt);
    }

    public void markOverdue() { this.status = LoanStatus.OVERDUE; }
    public void markLostDeclared() { this.status = LoanStatus.LOST_DECLARED; }

    public LoanId getId() { return id; }
    public CopyId getCopyId() { return copyId; }
    public UserId getUserId() { return userId; }
    public Instant getStartAt() { return startAt; }
    public Instant getDueAt() { return dueAt; }
    public Instant getReturnedAt() { return returnedAt; }
    public int getRenewCount() { return renewCount; }
    public LoanStatus getStatus() { return status; }
    public int getMaxRenewals() { return maxRenewals; }
}
