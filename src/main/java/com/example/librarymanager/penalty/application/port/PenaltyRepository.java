package com.example.librarymanager.penalty.application.port;

import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.domain.PenaltyStatus;
import com.example.librarymanager.user.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface PenaltyRepository {
    Penalty save(Penalty penalty);
    Optional<Penalty> findById(PenaltyId id);
    List<Penalty> findByUserId(UserId userId);
    List<Penalty> findByUserIdAndStatus(UserId userId, PenaltyStatus status);
    long countPendingByUserId(UserId userId);
}
