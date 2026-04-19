package com.example.librarymanager.work.infrastructure.rest.dto;

import com.example.librarymanager.work.domain.WorkType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateWorkRequest(
        String isbn,
        @NotBlank String title,
        @NotNull List<String> authors,
        String publisher,
        @Positive int year,
        List<String> subjects,
        String language,
        String description,
        @NotNull WorkType type
) {}
