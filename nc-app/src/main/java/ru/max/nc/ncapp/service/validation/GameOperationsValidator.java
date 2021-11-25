package ru.max.nc.ncapp.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.GameRepository;
import ru.max.nc.ncapp.data.MoveRepository;

import javax.persistence.EntityExistsException;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.IN_PROGRESS;
import static ru.max.nc.ncapp.api.dto.GameDto.Status.NEW;

@Service
@RequiredArgsConstructor
public class GameOperationsValidator {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;

    public void validateCreation(GameDto dto, String username) {
        gameRepository.findByName(dto.getName()).ifPresent(game -> {
            throw new EntityExistsException(
                    String.format("Game with name %s already exists. Creator %s",
                            dto.getName(), game.getCreatedBy()
                    )
            );
        });
    }

    public void validateJoin(Game game, String username) {
        validateGameStatus(game, NEW);
    }

    public void validateStart(Game game, String username) {
         validateGameStatus(game, NEW);
        if (!game.getCreatedBy().equals(username)) {
            throw new IllegalStateException("Game can be started only by creator");
        }
        if (!StringUtils.hasText(game.getSecondPlayer())) {
            throw new IllegalStateException("Can't start game without a second player");
        }
    }

    public void validateMove(MoveDto moveDto,Game game, String username) {
        validateGameStatus(game, IN_PROGRESS);
        if (!game.getNextMove().equals(username)) {
            throw new IllegalStateException(
                    String.format("Next move is to be done by player %s", game.getNextMove())
            );
        }
        if (moveDto.getXPos() > game.getFieldSize() ||
                moveDto.getYPos() > game.getFieldSize()) {
            throw new IllegalArgumentException("Move position is beyond the game field size = " + game.getFieldSize());
        }
        moveRepository.findByGameAndXPosAndYPos(game, moveDto.getXPos(), moveDto.getYPos())
                .ifPresent(move -> {
                    throw new IllegalStateException(
                            String.format("Move with position (%s, %s) was already done by %s",
                                    moveDto.getXPos(), moveDto.getYPos(), move.getUserName()
                            )
                    );
                });
    }

    private void validateGameStatus(Game game, GameDto.Status status) {
        if (!status.equals(game.getStatus())) {
            throw new IllegalStateException(
                    String.format("Illegal game status: game = %s, status = %s",
                            game.getName(), game.getStatus()
                    )
            );
        }
    }
}
