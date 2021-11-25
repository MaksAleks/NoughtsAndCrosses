package ru.max.nc.ncapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.GameConverter;
import ru.max.nc.ncapp.data.GameRepository;
import ru.max.nc.ncapp.service.validation.GameOperationsValidator;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static ru.max.nc.ncapp.service.GameTestDataFactory.*;

class GameApplicationServiceTest {

    GameApplicationService gameApplicationService;

    GameOperationsValidator operationsValidator;
    @Spy
    GameConverter gameConverter;
    @Mock
    GameRepository gameRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        operationsValidator = spy(new GameOperationsValidator(gameRepository));
        gameApplicationService = new GameApplicationService(
                operationsValidator,
                gameRepository,
                gameConverter
        );
    }

    @Test
    @DisplayName("User should be able to create a game with specific name if no game exists with the same name")
    public void shouldCreateGame() {
        //given
        var aNewGameDto = aNewDefaultGameDto().build();
        var aNewGame = aNewDefaultGame().build();
        UUID generatedID = UUID.randomUUID();
        Game savedGame = aNewGame.withId(generatedID);

        when(gameRepository.findByName(DEFAULT_GAME_NAME))
                .thenReturn(Optional.empty());
        when(gameRepository.save(eq(aNewGame)))
                .thenReturn(savedGame);

        //when
        GameDto createdGame = gameApplicationService.createGame(aNewGameDto, DEFAULT_USER);

        //then
        assertThat(createdGame).satisfies(created -> {
            assertThat(created.getId())
                    .isEqualTo(savedGame.getId());
            assertThat(created.getName())
                    .isEqualTo(savedGame.getName());
            assertThat(created.getFieldSize())
                    .isEqualTo(savedGame.getFieldSize());
            assertThat(created.getCreatedBy())
                    .isEqualTo(savedGame.getCreatedBy());
        });
    }

    @Test
    @DisplayName("User should be able to join an existing game if game is not started yet")
    public void shouldJoinGame() {
        //given
        var newExistingGame = aNewDefaultGame()
                .id(UUID.randomUUID())
                .build();
        String secondPlayer = "secondPlayer";
        Game withSecondPlayer = newExistingGame.withSecondPlayer(secondPlayer);
//        GameDto withSecondPlayerDto = aNewDefaultGameDto()p

        when(gameRepository.getByNameOrThrow(DEFAULT_GAME_NAME))
                .thenReturn(newExistingGame);
        when(gameRepository.save(eq(withSecondPlayer)))
                .thenReturn(withSecondPlayer);

        //when
        GameDto updatedGame = gameApplicationService.joinGame(DEFAULT_GAME_NAME, secondPlayer);

        //then
        assertThat(updatedGame).satisfies(created -> {
            assertThat(created.getId())
                    .isEqualTo(withSecondPlayer.getId());
            assertThat(created.getName())
                    .isEqualTo(withSecondPlayer.getName());
            assertThat(created.getFieldSize())
                    .isEqualTo(withSecondPlayer.getFieldSize());
            assertThat(created.getCreatedBy())
                    .isEqualTo(withSecondPlayer.getCreatedBy());
        });
    }
}