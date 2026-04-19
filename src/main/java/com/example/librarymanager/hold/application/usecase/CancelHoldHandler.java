package com.example.librarymanager.hold.application.usecase;

import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class CancelHoldHandler implements CancelHold {

    private final HoldRepository holdRepository;

    CancelHoldHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional
    public void handle(HoldId holdId) {
        Hold hold = holdRepository.findById(holdId)
                .orElseThrow(() -> new ResourceNotFoundException("Hold not found: " + holdId.value()));

        hold.cancel();
        holdRepository.save(hold);

        List<Hold> queued = holdRepository.findByWorkIdAndStatus(hold.getWorkId(), HoldStatus.QUEUED);
        queued.stream()
                .filter(h -> h.getQueuePosition() > hold.getQueuePosition())
                .forEach(h -> { h.decrementPosition(); holdRepository.save(h); });
    }
}
