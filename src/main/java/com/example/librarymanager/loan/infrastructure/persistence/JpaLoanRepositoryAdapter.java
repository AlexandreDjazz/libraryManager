package com.example.librarymanager.loan.infrastructure.persistence;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.loan.domain.LoanStatus;
import com.example.librarymanager.user.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaLoanRepositoryAdapter implements LoanRepository {

    private static final List<LoanStatus> ACTIVE_STATUSES = List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE);

    private final SpringJpaLoanRepository repo;

    public JpaLoanRepositoryAdapter(SpringJpaLoanRepository repo) {
        this.repo = repo;
    }

    @Override
    public Loan save(Loan loan) {
        return repo.save(JpaLoan.fromDomain(loan)).toDomain();
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        return repo.findById(id.value()).map(JpaLoan::toDomain);
    }

    @Override
    public Optional<Loan> findActiveByCopyId(CopyId copyId) {
        return repo.findByCopyIdAndStatusIn(copyId.value(), ACTIVE_STATUSES).map(JpaLoan::toDomain);
    }

    @Override
    public List<Loan> findActiveByUserId(UserId userId) {
        return repo.findByUserIdAndStatusIn(userId.value(), ACTIVE_STATUSES).stream()
                .map(JpaLoan::toDomain).toList();
    }

    @Override
    public long countActiveByUserId(UserId userId) {
        return repo.countByUserIdAndStatusIn(userId.value(), ACTIVE_STATUSES);
    }

    @Override
    public List<Loan> findByUserId(UserId userId) {
        return repo.findByUserId(userId.value()).stream().map(JpaLoan::toDomain).toList();
    }

    @Override
    public List<Loan> findByStatus(LoanStatus status) {
        return repo.findByStatus(status).stream().map(JpaLoan::toDomain).toList();
    }
}
