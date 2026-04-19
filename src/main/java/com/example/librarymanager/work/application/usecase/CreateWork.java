package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.work.domain.WorkId;
import com.example.librarymanager.work.domain.WorkType;

import java.util.List;

public interface CreateWork {
    record Command(String isbn, String title, List<String> authors, String publisher,
                   int year, List<String> subjects, String language,
                   String description, WorkType type) {}
    WorkId handle(Command command);
}
