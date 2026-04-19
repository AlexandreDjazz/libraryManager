package com.example.librarymanager.penalty.infrastructure.rest;

import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.penalty.application.usecase.ResolvePenalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.infrastructure.rest.dto.PenaltyResponse;
import com.example.librarymanager.user.domain.UserId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/penalties")
@SecurityRequirement(name = "Bearer Auth")
public class PenaltyController {

    private final PenaltyRepository penaltyRepository;
    private final ResolvePenalty resolvePenalty;

    public PenaltyController(PenaltyRepository penaltyRepository, ResolvePenalty resolvePenalty) {
        this.penaltyRepository = penaltyRepository;
        this.resolvePenalty = resolvePenalty;
    }

    @GetMapping("/my")
    public List<PenaltyResponse> myPenalties(@AuthenticationPrincipal UserDetails userDetails) {
        return penaltyRepository.findByUserId(UserId.of(userDetails.getUsername()))
                .stream().map(PenaltyResponse::from).toList();
    }

    @PatchMapping("/{id}/resolve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public void resolve(@PathVariable String id) {
        resolvePenalty.handle(PenaltyId.of(id));
    }
}
