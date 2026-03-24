package com.example.librarymanager.shared;

import java.time.Clock;
import java.time.Instant;

final class SystemUtcTimeProvider implements TimeProvider {
    private final Clock clock;

    public SystemUtcTimeProvider(Clock clock) {
        this.clock = clock;
    }

    public Instant now() {
        return Instant.now(clock);
    }
}
