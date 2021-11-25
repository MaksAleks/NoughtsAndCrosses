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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GameApplicationService {

    private final GameOperationsValidator operationsValidator;
    private final GameRepository gameRepository;
    private final GameConverter converter;

    public GameDto createGame(GameDto game, String name) {
        operationsValidator.validateCreation(game, name);
        Game build = converter.convertFromDto(game)
                .withCreatedBy(name);
        Game saved = gameRepository.save(build);
        return converter.convertToDto(saved);
    }

}


