package com.example.librarymanager.loan.infrastructure.rest;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.loan.application.usecase.BorrowCopy;
import com.example.librarymanager.loan.application.usecase.GetLoanHistory;
import com.example.librarymanager.loan.application.usecase.RenewLoan;
import com.example.librarymanager.loan.application.usecase.ReturnCopy;
import com.example.librarymanager.loan.domain.LoanId;
import com.example.librarymanager.loan.infrastructure.rest.dto.BorrowRequest;
import com.example.librarymanager.loan.infrastructure.rest.dto.LoanResponse;
import com.example.librarymanager.user.domain.UserId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@SecurityRequirement(name = "Bearer Auth")
public class LoanController {

    private final BorrowCopy borrowCopy;
    private final ReturnCopy returnCopy;
    private final RenewLoan renewLoan;
    private final GetLoanHistory getLoanHistory;

    public LoanController(BorrowCopy borrowCopy, ReturnCopy returnCopy,
                          RenewLoan renewLoan, GetLoanHistory getLoanHistory) {
        this.borrowCopy = borrowCopy;
        this.returnCopy = returnCopy;
        this.renewLoan = renewLoan;
        this.getLoanHistory = getLoanHistory;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String borrow(@Valid @RequestBody BorrowRequest request,
                         @AuthenticationPrincipal UserDetails userDetails) {
        return borrowCopy.handle(new BorrowCopy.Command(
                CopyId.of(request.copyId()), UserId.of(userDetails.getUsername()))).value();
    }

    @PatchMapping("/{id}/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnLoan(@PathVariable String id) {
        returnCopy.handle(LoanId.of(id));
    }

    @PatchMapping("/{id}/renew")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void renew(@PathVariable String id) {
        renewLoan.handle(LoanId.of(id));
    }

    @GetMapping("/history")
    public List<LoanResponse> history(@AuthenticationPrincipal UserDetails userDetails) {
        return getLoanHistory.handle(UserId.of(userDetails.getUsername()))
                .stream().map(LoanResponse::from).toList();
    }
}
