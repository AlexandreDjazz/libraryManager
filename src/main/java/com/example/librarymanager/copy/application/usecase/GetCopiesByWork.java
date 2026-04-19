package com.example.librarymanager.copy.application.usecase;

import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.work.domain.WorkId;

import java.util.List;

public interface GetCopiesByWork {
    List<Copy> handle(WorkId workId);
}
