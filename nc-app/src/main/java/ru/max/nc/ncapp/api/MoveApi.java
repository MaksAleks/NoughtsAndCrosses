package ru.max.nc.ncapp.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.service.MoveApplicationService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/game/move")
@RequiredArgsConstructor
public class MoveApi {

    private final MoveApplicationService moveApplicationService;

    @Operation(summary = "Make a move. First move is made by creator.")
    @PostMapping
    public void makeAMove(@Valid @RequestBody MoveDto moveDto, Principal principal) {
        moveApplicationService.makeAMove(moveDto, principal.getName());
    }

    @GetMapping("/{gameName}")
    public List<MoveDto> getMovesForGame(@PathVariable("gameName") String gameName) {
        return moveApplicationService.getMovesForGame(gameName);
    }
}
