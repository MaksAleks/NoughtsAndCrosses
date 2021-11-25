package ru.max.nc.ncapp.service;

import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.NEW;

public class GameTestDataFactory {

    public static GameDto.Status DEFAULT_GAME_STATUS = NEW;
    public static String DEFAULT_GAME_NAME = "game";
    public static String DEFAULT_USER = "user";
    public static int DEFAULT_FIELD_SIZE = 3;

    public static Game.GameBuilder aNewDefaultGame() {
        return Game.builder()
                .status(DEFAULT_GAME_STATUS)
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER);
    }

    public static GameDto.GameDtoBuilder aNewDefaultGameDto() {
        return GameDto.builder()
                .status(DEFAULT_GAME_STATUS)
                .name(DEFAULT_GAME_NAME)
                .fieldSize(DEFAULT_FIELD_SIZE)
                .createdBy(DEFAULT_USER);
    }

}
