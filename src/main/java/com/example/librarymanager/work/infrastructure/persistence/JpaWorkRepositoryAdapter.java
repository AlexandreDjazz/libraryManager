package com.example.librarymanager.work.infrastructure.persistence;

import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;
import com.example.librarymanager.work.domain.WorkType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaWorkRepositoryAdapter implements WorkRepository {

    private final SpringJpaWorkRepository repo;

    public JpaWorkRepositoryAdapter(SpringJpaWorkRepository repo) {
        this.repo = repo;
    }

    @Override
    public Work save(Work work) {
        return repo.save(JpaWork.fromDomain(work)).toDomain();
    }

    @Override
    public Optional<Work> findById(WorkId id) {
        return repo.findById(id.value()).map(JpaWork::toDomain);
    }

    @Override
    public Optional<Work> findByIsbn(String isbn) {
        return repo.findByIsbn(isbn).map(JpaWork::toDomain);
    }

    @Override
    public List<Work> search(String keyword, WorkType type, String language, Integer year) {
        return repo.search(keyword, type, language, year).stream()
                .map(JpaWork::toDomain).toList();
    }

    @Override
    public void delete(WorkId id) {
        repo.deleteById(id.value());
    }

    @Override
    public boolean existsById(WorkId id) {
        return repo.existsById(id.value());
    }
}
