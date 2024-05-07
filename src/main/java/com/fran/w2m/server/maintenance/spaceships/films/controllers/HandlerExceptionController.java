package com.fran.w2m.server.maintenance.spaceships.films.controllers;

import com.fran.w2m.server.maintenance.spaceships.films.dto.Error;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.ValidationException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.fran.w2m.server.maintenance.spaceships.films.utils.SpaceshipUtil.NOT_FOUND_SPACESHIP;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.SpaceshipUtil.SPACESHIP_MODEL;


@ControllerAdvice
public class HandlerExceptionController {

    @ApiResponse(responseCode = "404", description = "No se encuentra la nave espacial solicitada")
    @ExceptionHandler(NotFoundSpaceshipException.class)
    public ResponseEntity<Error> handleNotFoundSpaceshipException(NotFoundSpaceshipException e) {
        final Error error = Error.builder()
                .date(LocalDateTime.now())
                .defaultMessage(NOT_FOUND_SPACESHIP)
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ApiResponse(responseCode = "400", description = "Error en el modelo de datos de la nave espacial")
    @ExceptionHandler(SpaceshipModelException.class)
    public ResponseEntity<Error> handlerSpaceshipModelException(SpaceshipModelException e) {
        final Error error = Error.builder()
                .date(LocalDateTime.now())
                .defaultMessage(SPACESHIP_MODEL)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ApiResponse(responseCode = "400", description = "Errores de validación en los datos proporcionados")
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> handleValidationException(ValidationException e) {
        final Error error = Error.builder()
                .date(LocalDateTime.now())
                .defaultMessage("Errores de validación detectados")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
