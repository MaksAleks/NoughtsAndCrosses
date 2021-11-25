package ru.max.nc.ncapp.data;

import org.springframework.stereotype.Component;
import ru.max.nc.ncapp.api.dto.MoveDto;

@Component
public class MoveConverter {

    public Move convertFromDto(MoveDto dto) {
        return Move.builder()
                .posX(dto.getXPos())
                .posY(dto.getYPos())
                .userName(dto.getUserName())
                .build();
    }

    public MoveDto convertToDto(Move move) {
        return MoveDto.builder()
                .xPos(move.getPosX())
                .yPos(move.getPosY())
                .userName(move.getUserName())
                .gameName(move.getGame().getName())
                .build();
    }
}
