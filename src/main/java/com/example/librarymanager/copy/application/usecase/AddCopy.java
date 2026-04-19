package com.example.librarymanager.copy.application.usecase;

import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.work.domain.WorkId;

public interface AddCopy {
    record Command(WorkId workId, String barcode, String campusId, String shelf, String condition) {}
    CopyId handle(Command command);
}
