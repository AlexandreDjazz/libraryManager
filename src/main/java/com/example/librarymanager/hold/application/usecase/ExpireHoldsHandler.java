package com.example.librarymanager.hold.application.usecase;

import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.shared.TimeProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpireHoldsHandler {

    private final HoldRepository holdRepository;
    private final TimeProvider timeProvider;

    public ExpireHoldsHandler(HoldRepository holdRepository, TimeProvider timeProvider) {
        this.holdRepository = holdRepository;
        this.timeProvider = timeProvider;
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void expireOverdueHolds() {
        var now = timeProvider.now();
        List<Hold> readyHolds = holdRepository.findByStatus(HoldStatus.READY_FOR_PICKUP);
        readyHolds.stream()
                .filter(h -> h.isExpired(now))
                .forEach(h -> { h.expire(); holdRepository.save(h); });

        List<Hold> nextQueued = holdRepository.findByStatus(HoldStatus.QUEUED);
        // Notify first in queue — actual notification not implemented yet
        nextQueued.stream()
                .filter(h -> h.getQueuePosition() == 1)
                .forEach(h -> {
                    h.markReadyForPickup(now.plusSeconds(3L * 24 * 3600));
                    holdRepository.save(h);
                });
    }
}
