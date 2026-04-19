package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.loan.application.port.LoanRepository;
import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.user.domain.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class GetLoanHistoryHandler implements GetLoanHistory {

    private final LoanRepository loanRepository;

    GetLoanHistoryHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> handle(UserId userId) {
        return loanRepository.findByUserId(userId);
    }
}
