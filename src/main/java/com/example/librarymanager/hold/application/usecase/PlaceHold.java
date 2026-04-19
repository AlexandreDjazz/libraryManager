package com.example.librarymanager.hold.application.usecase;

import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;

public interface PlaceHold {
    record Command(WorkId workId, UserId userId) {}
    HoldId handle(Command command);
}
