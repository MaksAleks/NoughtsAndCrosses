package ru.max.nc.ncapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.GameConverter;
import ru.max.nc.ncapp.data.GameRepository;
import ru.max.nc.ncapp.service.validation.GameOperationsValidator;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.IN_PROGRESS;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GameApplicationService {

    private final GameOperationsValidator operationsValidator;
    private final GameRepository gameRepository;
    private final GameConverter converter;

    public GameDto createGame(GameDto game, String username) {
        operationsValidator.validateCreation(game, username);
        return update(converter.convertFromDto(game)
                .withCreatedBy(username));
    }

    public GameDto joinGame(String gameName, String username) {
        Game game = gameRepository.getByNameOrThrow(gameName);
        operationsValidator.validateJoin(game, username);
        return update(game.withSecondPlayer(username));
    }

    public GameDto startGame(String gameName, String username) {
        Game game = gameRepository.getByNameOrThrow(gameName);
        operationsValidator.validateStart(game, username);
        return update(game.withStatus(IN_PROGRESS));
    }

    private GameDto update(Game game) {
        return converter.convertToDto(gameRepository.save(game));
    }
}


