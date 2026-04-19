package com.example.librarymanager.hold.application.usecase;

import com.example.librarymanager.hold.domain.HoldId;

public interface CancelHold {
    void handle(HoldId holdId);
}
