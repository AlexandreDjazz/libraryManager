package com.example.librarymanager.hold.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.TimeProvider;
import com.example.librarymanager.shared.exception.BusinessRuleException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.work.application.port.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class PlaceHoldHandler implements PlaceHold {

    private final HoldRepository holdRepository;
    private final WorkRepository workRepository;
    private final CopyRepository copyRepository;
    private final DomainIdGenerator idGenerator;
    private final TimeProvider timeProvider;

    PlaceHoldHandler(HoldRepository holdRepository, WorkRepository workRepository,
                     CopyRepository copyRepository, DomainIdGenerator idGenerator,
                     TimeProvider timeProvider) {
        this.holdRepository = holdRepository;
        this.workRepository = workRepository;
        this.copyRepository = copyRepository;
        this.idGenerator = idGenerator;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public HoldId handle(Command command) {
        if (!workRepository.existsById(command.workId())) {
            throw new ResourceNotFoundException("Work not found: " + command.workId().value());
        }

        holdRepository.findActiveByWorkIdAndUserId(command.workId(), command.userId())
                .ifPresent(h -> { throw new BusinessRuleException("User already has an active hold on this work"); });

        var now = timeProvider.now();
        long queueSize = holdRepository.countActiveByWorkId(command.workId());
        boolean copyAvailable = !copyRepository.findByWorkIdAndStatus(
                command.workId(), CopyStatus.AVAILABLE).isEmpty();

        HoldStatus initialStatus;
        int position;
        java.time.Instant pickupUntil = null;

        if (copyAvailable && queueSize == 0) {
            initialStatus = HoldStatus.READY_FOR_PICKUP;
            position = 0;
            pickupUntil = now.plusSeconds(3L * 24 * 3600);
        } else {
            initialStatus = HoldStatus.QUEUED;
            position = (int) queueSize + 1;
        }

        Hold hold = new Hold(HoldId.of(idGenerator.generate()), command.workId(),
                command.userId(), initialStatus, position, pickupUntil, now);
        return holdRepository.save(hold).getId();
    }
}
