package com.example.librarymanager.loan.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record BorrowRequest(@NotBlank String copyId) {}
