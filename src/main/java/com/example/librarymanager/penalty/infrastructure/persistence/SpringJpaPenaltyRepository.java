package com.example.librarymanager.penalty.infrastructure.persistence;

import com.example.librarymanager.penalty.domain.PenaltyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SpringJpaPenaltyRepository extends JpaRepository<JpaPenalty, String> {
    List<JpaPenalty> findByUserId(String userId);
    List<JpaPenalty> findByUserIdAndStatus(String userId, PenaltyStatus status);
    long countByUserIdAndStatus(String userId, PenaltyStatus status);
}
