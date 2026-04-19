package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.shared.exception.ResourceNotFoundException;
import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class GetWorkByIdHandler implements GetWorkById {

    private final WorkRepository workRepository;

    GetWorkByIdHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Work handle(WorkId id) {
        return workRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found: " + id.value()));
    }
}
