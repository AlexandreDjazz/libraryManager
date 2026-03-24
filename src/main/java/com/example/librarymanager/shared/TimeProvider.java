package com.example.librarymanager.shared;

import java.time.Instant;

public interface TimeProvider {
    Instant now();
}
