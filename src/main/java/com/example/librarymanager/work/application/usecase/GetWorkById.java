package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;

public interface GetWorkById {
    Work handle(WorkId id);
}
