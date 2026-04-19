package com.example.librarymanager.hold.application.port;

import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;

import java.util.List;
import java.util.Optional;

public interface HoldRepository {
    Hold save(Hold hold);
    Optional<Hold> findById(HoldId id);
    List<Hold> findByWorkIdAndStatus(WorkId workId, HoldStatus status);
    Optional<Hold> findActiveByWorkIdAndUserId(WorkId workId, UserId userId);
    long countActiveByWorkId(WorkId workId);
    List<Hold> findByStatus(HoldStatus status);
}
