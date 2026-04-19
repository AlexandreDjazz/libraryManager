package com.example.librarymanager.copy.application.usecase;

import com.example.librarymanager.copy.application.port.CopyRepository;
import com.example.librarymanager.copy.domain.Copy;
import com.example.librarymanager.work.domain.WorkId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class GetCopiesByWorkHandler implements GetCopiesByWork {

    private final CopyRepository copyRepository;

    GetCopiesByWorkHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Copy> handle(WorkId workId) {
        return copyRepository.findByWorkId(workId);
    }
}
