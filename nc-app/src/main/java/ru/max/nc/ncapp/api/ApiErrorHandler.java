package ru.max.nc.ncapp.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.max.nc.ncapp.api.dto.Errors;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Errors> handleException(Exception ex, WebRequest request) {
        var response = Errors.serverError(new Errors.Error(ex.getLocalizedMessage()));
        log.error("Unexpected error", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
