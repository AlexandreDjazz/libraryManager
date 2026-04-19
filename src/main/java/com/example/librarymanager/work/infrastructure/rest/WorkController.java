package com.example.librarymanager.work.infrastructure.rest;

import com.example.librarymanager.work.application.usecase.*;
import com.example.librarymanager.work.domain.WorkId;
import com.example.librarymanager.work.domain.WorkType;
import com.example.librarymanager.work.infrastructure.rest.dto.CreateWorkRequest;
import com.example.librarymanager.work.infrastructure.rest.dto.WorkResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/works")
@SecurityRequirement(name = "Bearer Auth")
public class WorkController {

    private final CreateWork createWork;
    private final SearchWorks searchWorks;
    private final GetWorkById getWorkById;
    private final UpdateWork updateWork;

    public WorkController(CreateWork createWork, SearchWorks searchWorks,
                          GetWorkById getWorkById, UpdateWork updateWork) {
        this.createWork = createWork;
        this.searchWorks = searchWorks;
        this.getWorkById = getWorkById;
        this.updateWork = updateWork;
    }

    @GetMapping
    public List<WorkResponse> search(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) WorkType type,
                                     @RequestParam(required = false) String language,
                                     @RequestParam(required = false) Integer year) {
        return searchWorks.handle(new SearchWorks.Query(keyword, type, language, year))
                .stream().map(WorkResponse::from).toList();
    }

    @GetMapping("/{id}")
    public WorkResponse getById(@PathVariable String id) {
        return WorkResponse.from(getWorkById.handle(WorkId.of(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public String create(@Valid @RequestBody CreateWorkRequest request) {
        var command = new CreateWork.Command(request.isbn(), request.title(), request.authors(),
                request.publisher(), request.year(), request.subjects(),
                request.language(), request.description(), request.type());
        return createWork.handle(command).value();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public void update(@PathVariable String id, @Valid @RequestBody CreateWorkRequest request) {
        var command = new UpdateWork.Command(WorkId.of(id), request.isbn(), request.title(),
                request.authors(), request.publisher(), request.year(), request.subjects(),
                request.language(), request.description(), request.type());
        updateWork.handle(command);
    }

}
