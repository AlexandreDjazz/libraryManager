package com.example.librarymanager.copy.domain;

import com.example.librarymanager.work.domain.WorkId;

public class Copy {
    private final CopyId id;
    private final String barcode;
    private final WorkId workId;
    private CopyStatus status;
    private String campusId;
    private String shelf;
    private String condition;

    public Copy(CopyId id, String barcode, WorkId workId, CopyStatus status,
                String campusId, String shelf, String condition) {
        this.id = id;
        this.barcode = barcode;
        this.workId = workId;
        this.status = status;
        this.campusId = campusId;
        this.shelf = shelf;
        this.condition = condition;
    }

    public boolean isAvailable() {
        return status == CopyStatus.AVAILABLE;
    }

    public void setStatus(CopyStatus status) {
        this.status = status;
    }

    public CopyId getId() { return id; }
    public String getBarcode() { return barcode; }
    public WorkId getWorkId() { return workId; }
    public CopyStatus getStatus() { return status; }
    public String getCampusId() { return campusId; }
    public String getShelf() { return shelf; }
    public String getCondition() { return condition; }
}
