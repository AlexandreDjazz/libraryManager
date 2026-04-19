package com.example.librarymanager.loan.application.port;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.loan.domain.LoanStatus;
import com.example.librarymanager.user.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(LoanId id);
    Optional<Loan> findActiveByCopyId(CopyId copyId);
    List<Loan> findActiveByUserId(UserId userId);
    long countActiveByUserId(UserId userId);
    List<Loan> findByUserId(UserId userId);
    List<Loan> findByStatus(LoanStatus status);
}
