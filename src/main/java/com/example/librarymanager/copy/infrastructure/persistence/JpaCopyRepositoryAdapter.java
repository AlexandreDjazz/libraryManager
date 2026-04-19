package com.example.librarymanager.copy.infrastructure.persistence;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCopyRepositoryAdapter implements CopyRepository {

    private final SpringJpaCopyRepository repo;

    public JpaCopyRepositoryAdapter(SpringJpaCopyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Copy save(Copy copy) {
        return repo.save(JpaCopy.fromDomain(copy)).toDomain();
    }

    @Override
    public Optional<Copy> findById(CopyId id) {
        return repo.findById(id.value()).map(JpaCopy::toDomain);
    }

    @Override
    public Optional<Copy> findByBarcode(String barcode) {
        return repo.findByBarcode(barcode).map(JpaCopy::toDomain);
    }

    @Override
    public List<Copy> findByWorkId(WorkId workId) {
        return repo.findByWorkId(workId.value()).stream().map(JpaCopy::toDomain).toList();
    }

    @Override
    public List<Copy> findByWorkIdAndStatus(WorkId workId, CopyStatus status) {
        return repo.findByWorkIdAndStatus(workId.value(), status).stream()
                .map(JpaCopy::toDomain).toList();
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return repo.existsByBarcode(barcode);
    }
}
