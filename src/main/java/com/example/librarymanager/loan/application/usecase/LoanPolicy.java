package com.example.librarymanager.loan.application.usecase;

import com.example.librarymanager.user.domain.UserCategory;

public record LoanPolicy(int maxLoans, int loanDays, int maxRenewals) {

    public static LoanPolicy forCategory(UserCategory category) {
        return switch (category) {
            case STUDENT -> new LoanPolicy(5, 21, 2);
            case TEACHER, RESEARCHER -> new LoanPolicy(20, 60, 3);
        };
    }
}
