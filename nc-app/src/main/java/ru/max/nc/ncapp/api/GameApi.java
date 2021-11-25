package ru.max.nc.ncapp.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.service.GameApplicationService;

import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequestMapping(GameApi.GAME_API_PATH)
@RequiredArgsConstructor
public class GameApi {
    public static final String GAME_API_PATH ="/game";

    private final GameApplicationService gameApplicationService;

    @Operation(summary = "Game creation endpoint. Creates a game with specified field size")
    @PostMapping
    public GameDto createGame(@Valid @RequestBody GameDto game, Principal principal) {
        return gameApplicationService.createGame(game, principal.getName());
    }

    @PostMapping("/join/{name}")
    public GameDto joinGame(@PathVariable("name") String gameName, Principal principal) {
        return gameApplicationService.joinGame(gameName, principal.getName());
    }

}
