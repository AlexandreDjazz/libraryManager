package com.example.librarymanager.loan.infrastructure.persistence;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.loan.domain.LoanStatus;
import com.example.librarymanager.user.domain.UserId;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "loan")
class JpaLoan {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "copy_id", nullable = false)
    private String copyId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "due_at", nullable = false)
    private Instant dueAt;

    @Column(name = "returned_at")
    private Instant returnedAt;

    @Column(name = "renew_count", nullable = false)
    private int renewCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @Column(name = "max_renewals", nullable = false)
    private int maxRenewals;

    protected JpaLoan() {}

    static JpaLoan fromDomain(Loan loan) {
        JpaLoan e = new JpaLoan();
        e.id = loan.getId().value();
        e.copyId = loan.getCopyId().value();
        e.userId = loan.getUserId().value();
        e.startAt = loan.getStartAt();
        e.dueAt = loan.getDueAt();
        e.returnedAt = loan.getReturnedAt();
        e.renewCount = loan.getRenewCount();
        e.status = loan.getStatus();
        e.maxRenewals = loan.getMaxRenewals();
        return e;
    }

    Loan toDomain() {
        return new Loan(LoanId.of(id), CopyId.of(copyId), UserId.of(userId),
                startAt, dueAt, returnedAt, renewCount, status, maxRenewals);
    }
}
