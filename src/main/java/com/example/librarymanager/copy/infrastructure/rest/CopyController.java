package com.example.librarymanager.copy.infrastructure.rest;

import com.example.librarymanager.copy.application.usecase.AddCopy;
import com.example.librarymanager.copy.application.usecase.GetCopiesByWork;
import com.example.librarymanager.copy.infrastructure.rest.dto.AddCopyRequest;
import com.example.librarymanager.copy.infrastructure.rest.dto.CopyResponse;
import com.example.librarymanager.work.domain.WorkId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/works/{workId}/copies")
@SecurityRequirement(name = "Bearer Auth")
public class CopyController {

    private final AddCopy addCopy;
    private final GetCopiesByWork getCopiesByWork;

    public CopyController(AddCopy addCopy, GetCopiesByWork getCopiesByWork) {
        this.addCopy = addCopy;
        this.getCopiesByWork = getCopiesByWork;
    }

    @GetMapping
    public List<CopyResponse> getCopies(@PathVariable String workId) {
        return getCopiesByWork.handle(WorkId.of(workId)).stream()
                .map(CopyResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public String addCopy(@PathVariable String workId, @Valid @RequestBody AddCopyRequest request) {
        var command = new AddCopy.Command(WorkId.of(workId), request.barcode(),
                request.campusId(), request.shelf(), request.condition());
        return addCopy.handle(command).value();
    }
}
