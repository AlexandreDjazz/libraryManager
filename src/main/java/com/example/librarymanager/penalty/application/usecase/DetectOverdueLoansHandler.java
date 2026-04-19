package com.example.librarymanager.penalty.application.usecase;

import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanStatus;
import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.domain.PenaltyStatus;
import com.example.librarymanager.penalty.domain.PenaltyType;
import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetectOverdueLoansHandler {

    private static final BigDecimal DAILY_FEE = new BigDecimal("0.50");

    private final LoanRepository loanRepository;
    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;
    private final DomainIdGenerator idGenerator;
    private final TimeProvider timeProvider;

    public DetectOverdueLoansHandler(LoanRepository loanRepository, PenaltyRepository penaltyRepository,
                                     UserRepository userRepository, DomainIdGenerator idGenerator,
                                     TimeProvider timeProvider) {
        this.loanRepository = loanRepository;
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.timeProvider = timeProvider;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void detectOverdue() {
        var now = timeProvider.now();
        List<Loan> activeLoans = loanRepository.findByStatus(LoanStatus.ACTIVE);

        activeLoans.stream()
                .filter(loan -> loan.isOverdue(now))
                .forEach(loan -> {
                    loan.markOverdue();
                    loanRepository.save(loan);

                    Penalty penalty = new Penalty(PenaltyId.of(idGenerator.generate()),
                            loan.getUserId(), PenaltyType.LATE_RETURN,
                            DAILY_FEE, "Overdue loan: " + loan.getId().value(),
                            PenaltyStatus.PENDING, now);
                    penaltyRepository.save(penalty);

                    userRepository.findById(loan.getUserId()).ifPresent(user -> {
                        user.block();
                        userRepository.save(user);
                    });
                });
    }
}
