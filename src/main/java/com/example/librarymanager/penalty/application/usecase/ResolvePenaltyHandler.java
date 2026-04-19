package com.example.librarymanager.penalty.application.usecase;

import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class ResolvePenaltyHandler implements ResolvePenalty {

    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;

    ResolvePenaltyHandler(PenaltyRepository penaltyRepository, UserRepository userRepository) {
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(PenaltyId penaltyId) {
        Penalty penalty = penaltyRepository.findById(penaltyId)
                .orElseThrow(() -> new ResourceNotFoundException("Penalty not found: " + penaltyId.value()));

        penalty.resolve();
        penaltyRepository.save(penalty);

        long remaining = penaltyRepository.countPendingByUserId(penalty.getUserId());
        if (remaining == 0) {
            User user = userRepository.findById(penalty.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            user.unblock();
            userRepository.save(user);
        }
    }
}
