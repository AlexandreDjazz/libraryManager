package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.work.domain.WorkId;

public interface DeleteWork {
    void handle(WorkId id);
}
