package ru.max.nc.ncapp.api.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Errors {

    @Singular
    List<Error> errors;

    public static Errors serverError(Error error) {
        return Errors.builder()
                .error(error)
                .build();
    }

    @Value
    @Jacksonized
    public static class Error {
        String message;
    }
}
