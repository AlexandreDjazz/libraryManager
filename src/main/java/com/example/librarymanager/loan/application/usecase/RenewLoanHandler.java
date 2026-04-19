package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.shared.exception.BusinessRuleException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class RenewLoanHandler implements RenewLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final HoldRepository holdRepository;
    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;
    private final TimeProvider timeProvider;

    RenewLoanHandler(LoanRepository loanRepository, CopyRepository copyRepository,
                     HoldRepository holdRepository, PenaltyRepository penaltyRepository,
                     UserRepository userRepository, TimeProvider timeProvider) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.holdRepository = holdRepository;
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public void handle(LoanId loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId.value()));

        Copy copy = copyRepository.findById(loan.getCopyId())
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        boolean hasPendingHold = !holdRepository.findByWorkIdAndStatus(
                copy.getWorkId(), HoldStatus.QUEUED).isEmpty();

        long pendingPenalties = penaltyRepository.countPendingByUserId(loan.getUserId());
        boolean hasBlockingPenalty = pendingPenalties > 0;

        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.isBlocked()) {
            throw new BusinessRuleException("User account is blocked");
        }

        if (!loan.canRenew(hasPendingHold, hasBlockingPenalty)) {
            throw new BusinessRuleException("Renewal not allowed");
        }

        LoanPolicy policy = LoanPolicy.forCategory(user.getCategory());
        var newDueAt = timeProvider.now().plusSeconds((long) policy.loanDays() * 24 * 3600);
        loan.renew(newDueAt);
        loanRepository.save(loan);
    }
}
