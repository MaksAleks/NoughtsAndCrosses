package ru.max.nc.ncapp.service;

import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;

public class GameTestDataFactory {

    public static String DEFAULT_GAME_NAME = "game";
    public static String DEFAULT_USER = "user";
    public static int DEFAULT_FIELD_SIZE = 3;

    public static Game.GameBuilder aNewGame() {
        return Game.builder()
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER);
    }

    public static GameDto.GameDtoBuilder aNewGameDto() {
        return GameDto.builder()
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER);
    }

}
