package ru.max.nc.ncapp.service;

import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.api.dto.MoveDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.Move;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.NEW;

public class GameTestDataFactory {

    public static GameDto.Status DEFAULT_GAME_STATUS = NEW;
    public static String DEFAULT_GAME_NAME = "game";
    public static String DEFAULT_USER = "user";
    public static int DEFAULT_FIELD_SIZE = 3;
    public static int DEFAULT_X_POS = 1;
    public static int DEFAULT_Y_POS = 3;

    public static Game.GameBuilder aNewDefaultGame() {
        return Game.builder()
                .status(DEFAULT_GAME_STATUS)
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .leftMovesCount((long) DEFAULT_FIELD_SIZE * DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER)
                .nextMove(DEFAULT_USER);
    }

    public static GameDto.GameDtoBuilder aNewDefaultGameDto() {
        return GameDto.builder()
                .status(DEFAULT_GAME_STATUS)
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .leftMovesCount((long) DEFAULT_FIELD_SIZE * DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER)
                .nextMove(DEFAULT_USER);
    }

    public static Move.MoveBuilder aNewMove(Game game, MoveDto dto) {
        return Move.builder()
                .game(game)
                .posX(dto.getXPos())
                .posY(dto.getYPos())
                .userName(dto.getUserName());
    }

}
