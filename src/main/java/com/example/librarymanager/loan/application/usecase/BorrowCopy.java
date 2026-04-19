package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.user.domain.UserId;

public interface BorrowCopy {
    record Command(CopyId copyId, UserId userId) {}
    LoanId handle(Command command);
}
