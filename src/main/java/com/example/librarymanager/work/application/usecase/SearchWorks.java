package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkType;

import java.util.List;

public interface SearchWorks {
    record Query(String keyword, WorkType type, String language, Integer year) {}
    List<Work> handle(Query query);
}
