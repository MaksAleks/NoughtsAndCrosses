package ru.max.nc.ncapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public GameDto createGame(@Valid @RequestBody GameDto game, Principal principal) {
        return gameApplicationService.createGame(game, principal.getName());
    }

}
