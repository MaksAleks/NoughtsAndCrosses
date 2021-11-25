package ru.max.nc.ncapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.GameConverter;
import ru.max.nc.ncapp.data.GameRepository;
import ru.max.nc.ncapp.service.validation.GameOperationsValidator;

import javax.persistence.OptimisticLockException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.IN_PROGRESS;

@Slf4j
@Service
@Transactional
@Retryable(value = OptimisticLockException.class,
        maxAttempts = 2,
        backoff = @Backoff(100)
)
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

    /**
     * It's possible that OptimisticLockException would be thrown here
     * because two different users could concurrently join the game
     * and a user whose transaction finishes last would rewrite 'secondPlayer' column
     *
     * That's why we need
     */
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

    public GameDto getGame(String gameName) {
        return converter.convertToDto(gameRepository.getByNameOrThrow(gameName));
    }


    public List<GameDto> getGameByUser(String username) {
        return gameRepository.getByCreatedBy(username)
                .stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }
}


