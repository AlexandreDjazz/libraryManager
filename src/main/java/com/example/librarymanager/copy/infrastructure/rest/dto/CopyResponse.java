package com.example.librarymanager.copy.infrastructure.rest.dto;

import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyStatus;

public record CopyResponse(
        String id,
        String barcode,
        String workId,
        CopyStatus status,
        String campusId,
        String shelf,
        String condition
) {
    public static CopyResponse from(Copy copy) {
        return new CopyResponse(copy.getId().value(), copy.getBarcode(), copy.getWorkId().value(),
                copy.getStatus(), copy.getCampusId(), copy.getShelf(), copy.getCondition());
    }
}
