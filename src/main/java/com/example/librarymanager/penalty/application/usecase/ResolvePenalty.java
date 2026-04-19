package com.example.librarymanager.penalty.application.usecase;

import com.example.librarymanager.penalty.domain.PenaltyId;

public interface ResolvePenalty {
    void handle(PenaltyId penaltyId);
}
