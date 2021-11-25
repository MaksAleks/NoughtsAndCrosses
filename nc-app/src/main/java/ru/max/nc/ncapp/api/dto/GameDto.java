package ru.max.nc.ncapp.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@With
@Builder(toBuilder = true)
@Jacksonized
public class GameDto {

    @Schema(description = "Unique game id")
    UUID id;

    @NotNull
    @Schema(required = true, description = "Unique game name")
    String name;

    @NotNull
    @Schema(description = "Name of the game creator")
    String createdBy;

    @Schema(description = "Second player name")
    String secondPlayer;

    @Schema(description = "Player that can make a move. Maybe either creator or second player")
    String nextMove;

    @Schema(description = "Оставшееся общее количество ходов")
    long leftMovesCount;

    @NotNull
    @Builder.Default
    Status status = Status.NEW;

    @NotNull
    @Min(3)
    @Schema(description = "Field size. Game supports only square fields, therefore if fieldSize = 3, actual size is 3x3")
    int fieldSize;

    public enum Status {
        NEW,
        IN_PROGRESS,
        FINISHED
    }
}
