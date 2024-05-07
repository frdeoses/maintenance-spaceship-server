package com.fran.w2m.server.maintenance.spaceships.films.controllers;

import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import com.fran.w2m.server.maintenance.spaceships.films.dto.Error;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.fran.w2m.server.maintenance.spaceships.films.utils.SpaceshipUtil.NOT_FOUND_SPACESHIP;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.SpaceshipUtil.SPACESHIP_MODEL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(HandlerExceptionControllerTest.class)
class HandlerExceptionControllerTest {

    public static final String SPACESHIP_NOT_FOUND = "Spaceship not found";
    public static final String SPACESHIP_DUPLICATED_MODEL = "Spaceship duplicated model";

    @Test
    void whenNotFoundSpaceship_shouldReturnNotFoundSpaceshipException() throws Exception {
        NotFoundSpaceshipException notFoundSpaceshipException = new NotFoundSpaceshipException(SPACESHIP_NOT_FOUND);

        // Manually trigger the exception handler
        ResponseEntity<Error> response = new HandlerExceptionController().handleNotFoundSpaceshipException(notFoundSpaceshipException);

        // Check response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(SPACESHIP_NOT_FOUND, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(NOT_FOUND_SPACESHIP, response.getBody().getDefaultMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    void whenModelSpaceshipDuplicated_shouldReturnSpaceshipModelException() throws Exception {
        SpaceshipModelException spaceshipModelException = new SpaceshipModelException(SPACESHIP_DUPLICATED_MODEL);

        // Manually trigger the exception handler
        ResponseEntity<Error> response = new HandlerExceptionController().handlerSpaceshipModelException(spaceshipModelException);

        // Check response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(SPACESHIP_DUPLICATED_MODEL, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(SPACESHIP_MODEL, response.getBody().getDefaultMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

}