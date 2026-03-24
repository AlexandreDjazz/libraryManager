package com.example.librarymanager.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class TimeConfig {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    TimeProvider timeProvider(Clock clock) {
        return new SystemUtcTimeProvider(clock);
    }
}
