package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.shared.exception.ConflictException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UpdateWorkHandler implements UpdateWork {

    private final WorkRepository workRepository;

    UpdateWorkHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    @Transactional
    public void handle(Command command) {
        Work work = workRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Work not found: " + command.id().value()));

        if (command.isbn() != null) {
            workRepository.findByIsbn(command.isbn())
                    .filter(w -> !w.getId().equals(command.id()))
                    .ifPresent(w -> { throw new ConflictException("ISBN already exists: " + command.isbn()); });
        }

        work.update(command.isbn(), command.title(), command.authors(), command.publisher(),
                command.year(), command.subjects(), command.language(),
                command.description(), command.type());
        workRepository.save(work);
    }
}
