package ru.max.nc.ncapp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value
@Builder
@With
@Jacksonized
public class MoveDto {
    @NotNull
    @Min(1)
    @Schema(description = "position on game field by x axe")
    int xPos;
    @NotNull
    @Min(1)
    @Schema(description = "position on game field by y axe")
    int yPos;
    @NotNull
    @Schema(description = "game name")
    String gameName;
    @Schema(description = "Name of the user who made the move")
    String userName;
    Instant createdTime;
}
