package ru.max.nc.ncapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.data.*;
import ru.max.nc.ncapp.service.validation.GameOperationsValidator;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static ru.max.nc.ncapp.api.dto.GameDto.Status.IN_PROGRESS;
import static ru.max.nc.ncapp.service.GameTestDataFactory.*;

class MoveApplicationServiceTest {

    MoveApplicationService moveApplicationService;

    GameOperationsValidator operationsValidator;
    @Spy
    MoveConverter moveConverter;
    @Mock
    GameRepository gameRepository;
    @Mock
    MoveRepository moveRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        operationsValidator = spy(new GameOperationsValidator(gameRepository, moveRepository));
        moveApplicationService = new MoveApplicationService(
                gameRepository,
                moveRepository,
                moveConverter,
                operationsValidator
        );
    }

    @Test
    @DisplayName("Player should be able to make a move")
    public void shouldMakeAMove() {
        //given
        String nextMovePlayer = "nextMovePlayer";
        var exisingGameInProgress = aNewDefaultGame()
                .id(UUID.randomUUID())
                .secondPlayer(nextMovePlayer)
                .nextMove(nextMovePlayer)
                .status(IN_PROGRESS)
                .build();
        var moveDto = MoveDto.builder()
                .xPos(1)
                .yPos(3)
                .gameName(DEFAULT_GAME_NAME)
                .build();
        Game updatedGame = exisingGameInProgress.withNextMove(DEFAULT_USER)
                .decrementLeftMovesCount();
        var move = aNewMove(updatedGame, moveDto)
                .userName(nextMovePlayer)
                .build();

        when(moveRepository.save(eq(move)))
                .thenReturn(move);
        when(gameRepository.getByNameOrThrow(DEFAULT_GAME_NAME))
                .thenReturn(exisingGameInProgress);
        when(moveRepository.findByGameAndXPosAndYPos(
                exisingGameInProgress, moveDto.getXPos(), moveDto.getYPos())
        ).thenReturn(Optional.empty());

        //when
        moveApplicationService.makeAMove(moveDto, nextMovePlayer);

        //then
        verify(operationsValidator, times(1))
                .validateMove(moveDto, exisingGameInProgress, nextMovePlayer);
        verify(moveRepository, times(1))
                .save(eq(move));
    }

    @Test
    @DisplayName("Player should be able to make a move")
    public void shouldFinishGame() {
        //given
        String nextMovePlayer = "nextMovePlayer";
        var lastMoveGame = aNewDefaultGame()
                .id(UUID.randomUUID())
                .leftMovesCount(1)
                .secondPlayer(nextMovePlayer)
                .nextMove(nextMovePlayer)
                .status(IN_PROGRESS)
                .build();
        var moveDto = MoveDto.builder()
                .xPos(1)
                .yPos(3)
                .gameName(DEFAULT_GAME_NAME)
                .build();
        Game finishedGame = lastMoveGame.toBuilder()
                .build()
                .decrementLeftMovesCount();
        var lastMove = aNewMove(finishedGame, moveDto)
                .userName(nextMovePlayer)
                .build();

        when(moveRepository.save(eq(lastMove)))
                .thenReturn(lastMove);
        when(gameRepository.getByNameOrThrow(DEFAULT_GAME_NAME))
                .thenReturn(lastMoveGame);
        when(moveRepository.findByGameAndXPosAndYPos(
                lastMoveGame, moveDto.getXPos(), moveDto.getYPos())
        ).thenReturn(Optional.empty());

        //when
        moveApplicationService.makeAMove(moveDto, nextMovePlayer);

        //then
        verify(operationsValidator, times(1))
                .validateMove(moveDto, lastMoveGame, nextMovePlayer);
        verify(moveRepository, times(1))
                .save(eq(lastMove));
    }

}