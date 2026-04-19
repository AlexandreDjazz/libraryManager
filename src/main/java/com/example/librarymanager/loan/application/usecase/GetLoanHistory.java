package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.loan.domain.Loan;
import com.example.librarymanager.user.domain.UserId;

import java.util.List;

public interface GetLoanHistory {
    List<Loan> handle(UserId userId);
}
