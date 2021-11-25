package ru.max.nc.ncapp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value
@Builder
@Jacksonized
public class MoveDto {
    @NotNull
    @Schema(description = "position on game field by x axe")
    int x;
    @NotNull
    @Schema(description = "position on game field by y axe")
    int y;
    @NotNull
    @Schema(description = "game name")
    String gameName;
}
