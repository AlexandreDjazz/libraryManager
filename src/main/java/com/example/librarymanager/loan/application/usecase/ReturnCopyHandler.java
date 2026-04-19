package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.shared.exception.BusinessRuleException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class ReturnCopyHandler implements ReturnCopy {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final HoldRepository holdRepository;
    private final TimeProvider timeProvider;

    ReturnCopyHandler(LoanRepository loanRepository, CopyRepository copyRepository,
                      HoldRepository holdRepository, TimeProvider timeProvider) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.holdRepository = holdRepository;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public void handle(LoanId loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId.value()));

        if (loan.getStatus().name().equals("RETURNED")) {
            throw new BusinessRuleException("Loan is already returned");
        }

        var now = timeProvider.now();
        loan.markReturned(now);
        loanRepository.save(loan);

        Copy copy = copyRepository.findById(loan.getCopyId())
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        List<Hold> queuedHolds = holdRepository.findByWorkIdAndStatus(
                copy.getWorkId(), HoldStatus.QUEUED);

        if (!queuedHolds.isEmpty()) {
            Hold nextHold = queuedHolds.getFirst();
            nextHold.markReadyForPickup(now.plusSeconds(3L * 24 * 3600));
            holdRepository.save(nextHold);
            copy.setStatus(CopyStatus.RESERVED);
        } else {
            copy.setStatus(CopyStatus.AVAILABLE);
        }
        copyRepository.save(copy);
    }
}
