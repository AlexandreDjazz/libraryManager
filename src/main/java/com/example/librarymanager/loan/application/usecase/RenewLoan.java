package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.loan.domain.LoanId;

public interface RenewLoan {
    void handle(LoanId loanId);
}
