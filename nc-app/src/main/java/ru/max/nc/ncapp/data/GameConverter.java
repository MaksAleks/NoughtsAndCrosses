package ru.max.nc.ncapp.data;

import org.springframework.stereotype.Component;
import ru.max.nc.ncapp.api.dto.GameDto;

@Component
public class GameConverter {

    public GameDto convertToDto(Game entity) {
        return GameDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .secondPlayer(entity.getSecondPlayer())
                .name(entity.getName())
                .createdBy(entity.getCreatedBy())
                .fieldSize(entity.getFieldSize())
                .build();
    }

    public Game convertFromDto(GameDto dto) {
        return Game.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .secondPlayer(dto.getSecondPlayer())
                .fieldSize(dto.getFieldSize())
                .createdBy(dto.getCreatedBy())
                .id(dto.getId())
                .build();
    }
}
