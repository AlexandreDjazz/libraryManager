package com.example.librarymanager.copy.application.port;

import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.work.domain.WorkId;

import java.util.List;
import java.util.Optional;

public interface CopyRepository {
    Copy save(Copy copy);
    Optional<Copy> findById(CopyId id);
    Optional<Copy> findByBarcode(String barcode);
    List<Copy> findByWorkId(WorkId workId);
    List<Copy> findByWorkIdAndStatus(WorkId workId, CopyStatus status);
    boolean existsByBarcode(String barcode);
}
