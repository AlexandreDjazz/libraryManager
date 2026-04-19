package com.example.librarymanager.work.application.usecase;

import com.example.librarymanager.work.application.port.WorkRepository;
import com.example.librarymanager.work.domain.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class SearchWorksHandler implements SearchWorks {

    private final WorkRepository workRepository;

    SearchWorksHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Work> handle(Query query) {
        return workRepository.search(query.keyword(), query.type(), query.language(), query.year());
    }
}
