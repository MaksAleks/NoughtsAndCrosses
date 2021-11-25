package ru.max.nc.ncapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.service.MoveApplicationService;

import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequestMapping("/game/move")
@RequiredArgsConstructor
public class MoveApi {

    private final MoveApplicationService moveApplicationService;

    @PostMapping
    public void makeAMove(@Valid @RequestBody MoveDto moveDto, Principal principal) {
        moveApplicationService.makeAMove(moveDto, principal.getName());
    }
}
