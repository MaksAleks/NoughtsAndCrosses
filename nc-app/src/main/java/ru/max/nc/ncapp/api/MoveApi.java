package ru.max.nc.ncapp.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.max.nc.ncapp.api.dto.MoveDto;

import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequestMapping("/game/move")
public class MoveApi {

    @PostMapping
    public void makeAMove(@Valid @RequestBody MoveDto moveDto, Principal principal) {

    }
}
