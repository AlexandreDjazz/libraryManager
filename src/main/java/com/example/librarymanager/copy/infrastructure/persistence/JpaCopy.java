package com.example.librarymanager.copy.infrastructure.persistence;

import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.work.domain.WorkId;
import jakarta.persistence.*;

@Entity
@Table(name = "copy")
class JpaCopy {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CopyStatus status;

    @Column(name = "campus_id")
    private String campusId;

    @Column(name = "shelf")
    private String shelf;

    @Column(name = "condition")
    private String condition;

    protected JpaCopy() {}

    static JpaCopy fromDomain(Copy copy) {
        JpaCopy e = new JpaCopy();
        e.id = copy.getId().value();
        e.barcode = copy.getBarcode();
        e.workId = copy.getWorkId().value();
        e.status = copy.getStatus();
        e.campusId = copy.getCampusId();
        e.shelf = copy.getShelf();
        e.condition = copy.getCondition();
        return e;
    }

    Copy toDomain() {
        return new Copy(CopyId.of(id), barcode, WorkId.of(workId),
                status, campusId, shelf, condition);
    }
}
