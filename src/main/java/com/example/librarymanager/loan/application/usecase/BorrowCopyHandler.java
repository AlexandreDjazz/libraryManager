package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.loan.domain.LoanStatus;
import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.shared.exception.BusinessRuleException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class BorrowCopyHandler implements BorrowCopy {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;
    private final DomainIdGenerator idGenerator;
    private final TimeProvider timeProvider;

    BorrowCopyHandler(LoanRepository loanRepository, CopyRepository copyRepository,
                      UserRepository userRepository, DomainIdGenerator idGenerator,
                      TimeProvider timeProvider) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public LoanId handle(Command command) {
        Copy copy = copyRepository.findById(command.copyId())
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found: " + command.copyId().value()));

        if (!copy.isAvailable()) {
            throw new BusinessRuleException("Copy is not available: " + command.copyId().value());
        }

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + command.userId().value()));

        if (user.isBlocked()) {
            throw new BusinessRuleException("User account is blocked");
        }

        LoanPolicy policy = LoanPolicy.forCategory(user.getCategory());
        long activeLoans = loanRepository.countActiveByUserId(command.userId());
        if (activeLoans >= policy.maxLoans()) {
            throw new BusinessRuleException("Loan quota exceeded: max " + policy.maxLoans());
        }

        var now = timeProvider.now();
        var dueAt = now.plusSeconds((long) policy.loanDays() * 24 * 3600);

        Loan loan = new Loan(LoanId.of(idGenerator.generate()), command.copyId(),
                command.userId(), now, dueAt, null, 0, LoanStatus.ACTIVE, policy.maxRenewals());

        copy.setStatus(CopyStatus.ON_LOAN);
        copyRepository.save(copy);

        return loanRepository.save(loan).getId();
    }
}
