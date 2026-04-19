package com.example.librarymanager.work.application.port;

import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;
import com.example.librarymanager.work.domain.WorkType;

import java.util.List;
import java.util.Optional;

public interface WorkRepository {
    Work save(Work work);
    Optional<Work> findById(WorkId id);
    Optional<Work> findByIsbn(String isbn);
    List<Work> search(String keyword, WorkType type, String language, Integer year);
    void delete(WorkId id);
    boolean existsById(WorkId id);
}
