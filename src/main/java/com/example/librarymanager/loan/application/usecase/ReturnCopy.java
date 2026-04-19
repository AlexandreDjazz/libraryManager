package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.loan.domain.LoanId;

public interface ReturnCopy {
    void handle(LoanId loanId);
}
