package ru.max.nc.ncapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.data.*;
import ru.max.nc.ncapp.service.validation.GameOperationsValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MoveApplicationService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final MoveConverter moveConverter;
    private final GameOperationsValidator gameOperationsValidator;

    public void makeAMove(MoveDto moveDto, String username) {
        Game game = gameRepository.getByNameOrThrow(moveDto.getGameName());
        gameOperationsValidator.validateMove(moveDto, game, username);
        Game updated = game.decrementLeftMovesCount();
        //TODO: find a winner for finished game
        if (updated.isNotFinished()) {
            updated.setNextMove(getNextMovePlayer(game, username));
        }
        Move move = moveConverter.convertFromDto(moveDto)
                .withUserName(username)
                .withGame(updated);
        moveRepository.save(move);
    }

    private String getNextMovePlayer(Game game, String username) {
        if (game.getSecondPlayer().equals(username)) {
            return game.getCreatedBy();
        } else {
            return game.getSecondPlayer();
        }
    }

    public List<MoveDto> getMovesForGame(String gameName) {
        Game game = gameRepository.getByNameOrThrow(gameName);
        return moveRepository.findByGame(game).stream()
                .map(moveConverter::convertToDto)
                .peek(dto -> dto.withGameName(gameName))
                .collect(Collectors.toList());
    }
}
