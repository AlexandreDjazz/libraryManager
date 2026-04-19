package com.example.librarymanager.penalty.infrastructure.persistence;

import com.example.librarymanager.penalty.application.port.PenaltyRepository;
import com.example.librarymanager.penalty.domain.Penalty;
import com.example.librarymanager.penalty.domain.PenaltyId;
import com.example.librarymanager.penalty.domain.PenaltyStatus;
import com.example.librarymanager.user.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaPenaltyRepositoryAdapter implements PenaltyRepository {

    private final SpringJpaPenaltyRepository repo;

    public JpaPenaltyRepositoryAdapter(SpringJpaPenaltyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Penalty save(Penalty penalty) {
        return repo.save(JpaPenalty.fromDomain(penalty)).toDomain();
    }

    @Override
    public Optional<Penalty> findById(PenaltyId id) {
        return repo.findById(id.value()).map(JpaPenalty::toDomain);
    }

    @Override
    public List<Penalty> findByUserId(UserId userId) {
        return repo.findByUserId(userId.value()).stream().map(JpaPenalty::toDomain).toList();
    }

    @Override
    public List<Penalty> findByUserIdAndStatus(UserId userId, PenaltyStatus status) {
        return repo.findByUserIdAndStatus(userId.value(), status).stream()
                .map(JpaPenalty::toDomain).toList();
    }

    @Override
    public long countPendingByUserId(UserId userId) {
        return repo.countByUserIdAndStatus(userId.value(), PenaltyStatus.PENDING);
    }
}
