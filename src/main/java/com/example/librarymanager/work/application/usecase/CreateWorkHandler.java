package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.exception.ConflictException;
import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CreateWorkHandler implements CreateWork {

    private final WorkRepository workRepository;
    private final DomainIdGenerator idGenerator;

    CreateWorkHandler(WorkRepository workRepository, DomainIdGenerator idGenerator) {
        this.workRepository = workRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public WorkId handle(Command command) {
        if (command.isbn() != null && workRepository.findByIsbn(command.isbn()).isPresent()) {
            throw new ConflictException("ISBN already exists: " + command.isbn());
        }
        Work work = new Work(WorkId.of(idGenerator.generate()), command.isbn(),
                command.title(), command.authors(), command.publisher(), command.year(),
                command.subjects(), command.language(), command.description(), command.type());
        return workRepository.save(work).getId();
    }
}
