package com.example.librarymanager.penalty.application.usecase;

import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.domain.PenaltyStatus;
import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CreatePenaltyHandler implements CreatePenalty {

    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;
    private final DomainIdGenerator idGenerator;
    private final TimeProvider timeProvider;

    CreatePenaltyHandler(PenaltyRepository penaltyRepository, UserRepository userRepository,
                         DomainIdGenerator idGenerator, TimeProvider timeProvider) {
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public PenaltyId handle(Command command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + command.userId().value()));

        Penalty penalty = new Penalty(PenaltyId.of(idGenerator.generate()), command.userId(),
                command.type(), command.amount(), command.reason(),
                PenaltyStatus.PENDING, timeProvider.now());
        penaltyRepository.save(penalty);

        user.block();
        userRepository.save(user);

        return penalty.getId();
    }
}
