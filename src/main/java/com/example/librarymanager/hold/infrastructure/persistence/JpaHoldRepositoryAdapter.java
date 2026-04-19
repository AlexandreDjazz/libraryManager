package com.example.librarymanager.hold.infrastructure.persistence;

import com.example.librarymanager.hold.application.port.HoldRepository;
import com.example.librarymanager.hold.domain.Hold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.domain.HoldStatus;
import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaHoldRepositoryAdapter implements HoldRepository {

    private final SpringJpaHoldRepository repo;

    public JpaHoldRepositoryAdapter(SpringJpaHoldRepository repo) {
        this.repo = repo;
    }

    @Override
    public Hold save(Hold hold) {
        return repo.save(JpaHold.fromDomain(hold)).toDomain();
    }

    @Override
    public Optional<Hold> findById(HoldId id) {
        return repo.findById(id.value()).map(JpaHold::toDomain);
    }

    @Override
    public List<Hold> findByWorkIdAndStatus(WorkId workId, HoldStatus status) {
        return repo.findByWorkIdAndStatusOrderByQueuePositionAsc(workId.value(), status).stream()
                .map(JpaHold::toDomain).toList();
    }

    @Override
    public Optional<Hold> findActiveByWorkIdAndUserId(WorkId workId, UserId userId) {
        return repo.findActiveByWorkIdAndUserId(workId.value(), userId.value())
                .map(JpaHold::toDomain);
    }

    @Override
    public long countActiveByWorkId(WorkId workId) {
        return repo.countActiveByWorkId(workId.value());
    }

    @Override
    public List<Hold> findByStatus(HoldStatus status) {
        return repo.findByStatus(status).stream().map(JpaHold::toDomain).toList();
    }
}
