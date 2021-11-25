package ru.max.nc.ncapp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@With
@Builder(toBuilder = true)
@Jacksonized
public class GameDto {

    @Schema(description = "Unique game id")
    UUID id;

    @Schema(required = true, description = "Unique game name")
    String name;

    String createdBy;

    int fieldSize;
}
