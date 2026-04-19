package com.example.librarymanager.copy.infrastructure.persistence;

import com.example.librarymanager.copy.domain.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface SpringJpaCopyRepository extends JpaRepository<JpaCopy, String> {
    Optional<JpaCopy> findByBarcode(String barcode);
    List<JpaCopy> findByWorkId(String workId);
    List<JpaCopy> findByWorkIdAndStatus(String workId, CopyStatus status);
    boolean existsByBarcode(String barcode);
}
