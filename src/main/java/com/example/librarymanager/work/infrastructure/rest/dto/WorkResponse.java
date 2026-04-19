package com.example.librarymanager.work.infrastructure.rest.dto;

import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkType;

import java.util.List;

public record WorkResponse(
        String id,
        String isbn,
        String title,
        List<String> authors,
        String publisher,
        int year,
        List<String> subjects,
        String language,
        String description,
        WorkType type
) {
    public static WorkResponse from(Work work) {
        return new WorkResponse(work.getId().value(), work.getIsbn(), work.getTitle(),
                work.getAuthors(), work.getPublisher(), work.getYear(), work.getSubjects(),
                work.getLanguage(), work.getDescription(), work.getType());
    }
}
