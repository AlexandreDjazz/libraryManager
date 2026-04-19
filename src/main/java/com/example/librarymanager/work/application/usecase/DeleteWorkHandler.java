package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.CopyStatus;
import com.example.librarymanager.shared.exception.BusinessRuleException;
import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class DeleteWorkHandler implements DeleteWork {

    private final WorkRepository workRepository;
    private final CopyRepository copyRepository;

    DeleteWorkHandler(WorkRepository workRepository, CopyRepository copyRepository) {
        this.workRepository = workRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional
    public void handle(WorkId id) {
        if (!workRepository.existsById(id)) {
            throw new ResourceNotFoundException("Work not found: " + id.value());
        }
        boolean hasActiveLoans = !copyRepository.findByWorkIdAndStatus(id, CopyStatus.ON_LOAN).isEmpty();
        if (hasActiveLoans) {
            throw new BusinessRuleException("Cannot delete work with active loans");
        }
        workRepository.delete(id);
    }
}
