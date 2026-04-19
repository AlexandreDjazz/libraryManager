package com.example.librarymanager.copy.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.copy.domain.CopyId;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.exception.ConflictException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.work.application.port.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AddCopyHandler implements AddCopy {

    private final CopyRepository copyRepository;
    private final WorkRepository workRepository;
    private final DomainIdGenerator idGenerator;

    AddCopyHandler(CopyRepository copyRepository, WorkRepository workRepository, DomainIdGenerator idGenerator) {
        this.copyRepository = copyRepository;
        this.workRepository = workRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public CopyId handle(Command command) {
        if (!workRepository.existsById(command.workId())) {
            throw new ResourceNotFoundException("Work not found: " + command.workId().value());
        }
        if (copyRepository.existsByBarcode(command.barcode())) {
            throw new ConflictException("Barcode already exists: " + command.barcode());
        }
        Copy copy = new Copy(CopyId.of(idGenerator.generate()), command.barcode(),
                command.workId(), CopyStatus.AVAILABLE,
                command.campusId(), command.shelf(), command.condition());
        return copyRepository.save(copy).getId();
    }
}
