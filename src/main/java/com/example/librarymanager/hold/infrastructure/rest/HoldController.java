package com.example.librarymanager.hold.infrastructure.rest;

import com.example.librarymanager.hold.application.usecase.CancelHold;
import com.example.librarymanager.hold.application.usecase.PlaceHold;
import com.example.librarymanager.hold.domain.HoldId;
import com.example.librarymanager.hold.infrastructure.rest.dto.PlaceHoldRequest;
import com.example.librarymanager.user.domain.UserId;
import com.example.librarymanager.work.domain.WorkId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/holds")
@SecurityRequirement(name = "Bearer Auth")
public class HoldController {

    private final PlaceHold placeHold;
    private final CancelHold cancelHold;

    public HoldController(PlaceHold placeHold, CancelHold cancelHold) {
        this.placeHold = placeHold;
        this.cancelHold = cancelHold;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeHold(@Valid @RequestBody PlaceHoldRequest request,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return placeHold.handle(new PlaceHold.Command(
                WorkId.of(request.workId()), UserId.of(userDetails.getUsername()))).value();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelHold(@PathVariable String id) {
        cancelHold.handle(HoldId.of(id));
    }
}
