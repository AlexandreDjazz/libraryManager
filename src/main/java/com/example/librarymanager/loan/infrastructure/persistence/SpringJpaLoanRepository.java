package com.example.librarymanager.loan.infrastructure.persistence;

import com.example.librarymanager.loan.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface SpringJpaLoanRepository extends JpaRepository<JpaLoan, String> {
    Optional<JpaLoan> findByCopyIdAndStatusIn(String copyId, List<LoanStatus> statuses);
    List<JpaLoan> findByUserIdAndStatusIn(String userId, List<LoanStatus> statuses);
    long countByUserIdAndStatusIn(String userId, List<LoanStatus> statuses);
    List<JpaLoan> findByUserId(String userId);
    List<JpaLoan> findByStatus(LoanStatus status);
}
