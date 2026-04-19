package com.example.librarymanager.copy.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record AddCopyRequest(
        @NotBlank String barcode,
        String campusId,
        String shelf,
        String condition
) {}
