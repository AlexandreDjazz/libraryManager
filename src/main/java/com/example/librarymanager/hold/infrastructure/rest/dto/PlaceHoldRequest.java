package com.example.librarymanager.hold.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceHoldRequest(@NotBlank String workId) {}
